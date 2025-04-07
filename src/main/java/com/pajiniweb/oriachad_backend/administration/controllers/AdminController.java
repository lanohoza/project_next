package com.pajiniweb.oriachad_backend.administration.controllers;

import com.pajiniweb.oriachad_backend.administration.domains.dtos.AddEditDtos.AddEditAdminDto;
import com.pajiniweb.oriachad_backend.administration.domains.entities.Admin;
import com.pajiniweb.oriachad_backend.administration.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody AddEditAdminDto dto) {
        Admin admin = adminService.createAdmin(dto);
        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody AddEditAdminDto dto) {
        Admin admin = adminService.updateAdmin(id, dto);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
