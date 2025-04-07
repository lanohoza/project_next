package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.ForgetPasswordService;
import com.pajiniweb.oriachad_backend.services.ForgetPasswordService.changePassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forget-password")
public class ForgetPasswordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordController.class);

    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String email) {
        LOGGER.info(Messages.START_FUNCTION, "verifyEmail");
        return forgetPasswordService.verifyEmail(email);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCodeOtp(@RequestParam String email, @RequestParam Integer codeOtp) {
        LOGGER.info(Messages.START_FUNCTION, "verifyCodeOtp");
        return forgetPasswordService.verifyCodeOtp(email, codeOtp);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody changePassword changePassword, @RequestParam String email) {
        LOGGER.info(Messages.START_FUNCTION, "changePassword");
        return forgetPasswordService.changePasswordHandler(changePassword, email);
    }
}
