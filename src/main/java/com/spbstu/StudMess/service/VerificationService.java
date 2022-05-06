package com.spbstu.StudMess.service;

import com.spbstu.StudMess.config.EmailProperties;
import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.exception.VerificationException;
import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final EmailProperties emailProperties;

    @Transactional(readOnly = true)
    public void sendVerificationEmail(@NonNull Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(UserEntity.class, userId.toString()));
        String toAddress = user.getLogin();

        String content = emailProperties.getContent();
        String verifyURL = emailProperties.getUrl();
        content = content.replace("[[name]]", toAddress.substring(0, toAddress.indexOf('@')));
        verifyURL = verifyURL.replace("[[userId]]", userId.toString());
        verifyURL = verifyURL.replace("[[code]]", user.getVerificationCode());
        content = content.replace("[[URL]]", verifyURL);

        try {
            MimeMessage message = getMimeMessage(toAddress, content);
            mailSender.send(message);
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            throw new VerificationException("Error sending the verification message to the mail");
        }
    }

    @NonNull
    private MimeMessage getMimeMessage(String toAddress, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(emailProperties.getEmail(), emailProperties.getName());
        helper.setSubject(emailProperties.getSubject());
        helper.setTo(toAddress);
        helper.setText(content, true);
        return message;
    }

    @Transactional
    public void verify(@NonNull Long userId, @NonNull String verificationCode) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(UserEntity.class, userId.toString()));

        if (user.getVerificationCode() == null) {
            throw new VerificationException("User already verified");
        }

        if (!user.getVerificationCode().equals(verificationCode)) {
            throw new VerificationException("Invalid verification code");
        }

        user.setVerificationCode(null);
        user.setEnabled(true);
        userRepository.save(user);
    }
}
