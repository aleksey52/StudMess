package com.spbstu.StudMess.controller.authcontroller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.service.VerificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "Verification")
@ApiV1
@RestController
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @GetMapping("/users/{userId}/verify")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView verifyUser(@NonNull @PathVariable Long userId,
                                   @NonNull @RequestParam("code") String verificationCode) {
        verificationService.verify(userId, verificationCode);
        return new ModelAndView("verification_page");
    }
}
