package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.EmailRequestDto;
import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.domains.entities.ForgetPassword;
import com.pajiniweb.oriachad_backend.repositories.ForgetPasswordRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class ForgetPasswordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordService.class);

    @Autowired
    ForgetPasswordRepository forgetPasswordRepository;

    @Autowired
    UserService userService;

    @Value("${ms.sendMailWithoutSendGrid.endPoint}")
    private String originalSendMailURL;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<String> verifyEmail(String email) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            UserDto user = userService.findByEmail(email);
            if (user == null) {
                return ResponseEntity.badRequest().body("Please provide a valid email");
            }
            int otp = codeOtpGenerator();
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            EmailRequestDto emailRequestDto = new EmailRequestDto();
            body.add("files", null);
            body.add("to", email);
            body.add("cc", null);
            body.add("subject", "Oublié Password");
            body.add("body","Ca c'est votre code de vérification pour réintialiser votre mot de passe : " + otp);

            ForgetPassword forgetPassword = ForgetPassword.builder()
                    .otp(otp)
                    .User(userService.mapUserDtoToEntity(user))
                    .expirationTime(new Date(System.currentTimeMillis() + 120_000))
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    originalSendMailURL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            forgetPasswordRepository.save(forgetPassword);
            return ResponseEntity.ok("Email is valid");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    public Integer codeOtpGenerator() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }

    public ResponseEntity<String> verifyCodeOtp(String email, Integer codeOtp) {
        UserDto user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Please provide a valid email");
        }

        ForgetPassword forgetPassword = forgetPasswordRepository.findByOtpAndUser(codeOtp, userService.mapUserDtoToEntity(user)).orElseThrow(
                () -> new RuntimeException("Invalid code for email : " + email));

        if (forgetPassword.getExpirationTime().before(Date.from(Instant.now()))) {
            forgetPasswordRepository.deleteById(forgetPassword.getId());
            return new ResponseEntity<>("code is expired", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("Code verified!");
    }

    public ResponseEntity<String> changePasswordHandler(changePassword changePassword, String email) {
        if (!Objects.equals(changePassword.password, changePassword.repeatPassword)) {
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password);

        userService.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password has been changed");
    }

    public record changePassword(String password, String repeatPassword) {

    }
}
