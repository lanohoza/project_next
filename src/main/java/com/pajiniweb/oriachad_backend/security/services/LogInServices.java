package com.pajiniweb.oriachad_backend.security.services;


import com.pajiniweb.oriachad_backend.administration.domains.dtos.AdminDto;
import com.pajiniweb.oriachad_backend.administration.domains.entities.Admin;
import com.pajiniweb.oriachad_backend.administration.services.AdminService;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditUserDto;
import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.security.domain.dtos.UserLoginDto;
import com.pajiniweb.oriachad_backend.security.domain.dtos.UserLoginResponseDto;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomAdminDetails;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import com.pajiniweb.oriachad_backend.services.UserService;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogInServices implements UserDetailsService {


    public Logger log = LoggerFactory.getLogger(LogInServices.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtService jwtService;

    public UserLoginResponseDto login(UserLoginDto userLogInDto) throws Exception {
        if (userLogInDto == null || userLogInDto.getEmail() == null || userLogInDto.getPassword() == null) {
            throw new IllegalArgumentException("Email and password must not be null");
        }

            OriachadUser user = userService.findByEmailAndPassword(userLogInDto);
            if (user != null) {
                if (user.getActive()) {
                    String token = jwtService.generateToken(user.getEmail() , "User");
                    return UserLoginResponseDto.builder().token(token).type("User").build();
                } else {
                    throw new Exception("error.user.not.active");
                }
            }

            throw new InvalidCredentialsException("error.login.fail");

    }

    public UserLoginResponseDto loginAdmin(UserLoginDto userLogInDto) throws Exception {

        if (userLogInDto == null || userLogInDto.getEmail() == null || userLogInDto.getPassword() == null) {
            throw new IllegalArgumentException("Email and password must not be null");
        }

        Admin admin = adminService.findByEmailAndPassword(userLogInDto);
        if (admin != null) {
            String token = jwtService.generateToken(admin.getEmail() , "Admin");
            return UserLoginResponseDto.builder().token(token).type("Admin").build();
        }

        throw new InvalidCredentialsException("error.login.fail");
    }


    private static List<GrantedAuthority> mapToGrantedAuthority(String rol) {
        List<String> rols = new ArrayList<>();
        rols.add(rol);
        return rols.stream().map(authority -> new SimpleGrantedAuthority("ROLE_" + authority)).collect(Collectors.toList());
    }


    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            log.info(Messages.START_FUNCTION, "load User By Username");
            OriachadUser userLogIn = userService.logInByEmail(email);
            return new CustomUserDetails(userLogIn);
        } catch (Exception e) {
            this.log.error("LogInServices.logIn " + e.getMessage());
        }
        return null;
    }

    public CustomAdminDetails loadAdminByUsername(String email) throws UsernameNotFoundException {
        try {
            log.info(Messages.START_FUNCTION, "load admin By Username");
            Admin adminLogIn = adminService.logInByEmail(email);
            return new CustomAdminDetails(adminLogIn);
        } catch (Exception e) {
            this.log.error("LogInServices.logIn " + e.getMessage());
        }
        return null;
    }

    public UserDto getLoginUser(CustomUserDetails userDetails) {

        return userService.mapUserEntityToDto(userDetails.getOriachadUser());
    }

    public AdminDto getLoginAdmin(CustomAdminDetails adminDetails) {

        return adminService.mapAdminEntityToDto(adminDetails.getOriachadAdmin());
    }

    public UserLoginResponseDto signup(AddEditUserDto userDto, MultipartFile files) throws Exception {
        System.out.println("list file :" + files);
        UserDto user = userService.createUser(userDto, files);
        String token = jwtService.generateToken(user.getEmail() , "User");
        return UserLoginResponseDto.builder().token(token).build();
    }
}
