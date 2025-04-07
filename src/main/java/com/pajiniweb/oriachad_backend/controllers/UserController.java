package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.services.UserService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    // Read all users
    @GetMapping("/all")
    public Page<UserDto> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size , @RequestParam(defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        return userService.getAllUsers(pageable , search);
    }

    // Read user by ID
    @GetMapping("/getUserById/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Update user
    @PutMapping("/update/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDetails) {
        return userService.updateUser(id, userDetails);
    }

    // Delete user
    @DeleteMapping("/delete/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    // Find by email
    @GetMapping("/findByEmail/{email}")
    public UserDto findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    // Edit password
    @PutMapping("/editPassword")
    public boolean editPassword(@RequestParam String currentPassword,
                                @RequestParam String newPassword) {
        return userService.editPassword(currentPassword, newPassword);
    }

    @PostMapping("/activeUnactiveUser/{state}")
    public Boolean findByEmail(
            @NotNull @RequestBody UserDto user,
            @PathVariable Boolean state,
            @RequestParam String reasonDesactivate) {
        return userService.activeUser(user, state, reasonDesactivate);
    }

    @PostMapping("/sendCertificateToAdministration")
    public ResponseEntity<Boolean> sendCertificateToAdministration(@RequestBody OriachadUser userDTO, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        Boolean isSended = userService.sendCertificateToAdministration(userDTO, file);
        return ResponseEntity.ok(isSended);
    }
}
