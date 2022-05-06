package com.spbstu.StudMess.security;

import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userService.findByEmail(email);
        return JwtUserFactory.create(user);
    }
}
