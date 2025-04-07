package com.pajiniweb.oriachad_backend.security.controllers;

import com.pajiniweb.oriachad_backend.administration.domains.dtos.AdminDto;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditUserDto;
import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.security.domain.dtos.UserLoginDto;
import com.pajiniweb.oriachad_backend.security.domain.dtos.UserLoginResponseDto;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomAdminDetails;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import com.pajiniweb.oriachad_backend.security.services.LogInServices;
import com.pajiniweb.oriachad_backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/authenticate")
public class AuthenticateController {

    public Logger log = LoggerFactory.getLogger(AuthenticateController.class);
    @Autowired
    private LogInServices logInServices;
    @Autowired
    UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<UserLoginResponseDto> authenticate(@RequestBody UserLoginDto userLogInDto) throws Exception {
        UserLoginResponseDto userLoginResponseDto = logInServices.login(userLogInDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @PostMapping("/authenticateAdmin")
    public ResponseEntity<UserLoginResponseDto> authenticateAdmin(@RequestBody UserLoginDto userLogInDto) throws Exception {
        UserLoginResponseDto userLoginResponseDto = logInServices.loginAdmin(userLogInDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @GetMapping("/auth")
    public ResponseEntity<UserDto> getUserData(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserDto userDto = logInServices.getLoginUser(userDetails);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/authAdmin")
    public ResponseEntity<AdminDto> getAdminData(@AuthenticationPrincipal CustomAdminDetails adminDetails) {
        AdminDto adminDto = logInServices.getLoginAdmin(adminDetails);
        return ResponseEntity.ok(adminDto);
    }

    @PostMapping(value = "/signUp", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserLoginResponseDto> createUser(@RequestPart("user") AddEditUserDto userDTO, @RequestParam(value = "file", required = false) MultipartFile files) throws Exception {
        UserLoginResponseDto userLoginResponseDto = logInServices.signup(userDTO, files);
        return ResponseEntity.ok(userLoginResponseDto);
    }

}
