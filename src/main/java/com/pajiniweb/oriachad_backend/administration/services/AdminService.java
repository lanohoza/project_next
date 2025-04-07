package com.pajiniweb.oriachad_backend.administration.services;

import com.pajiniweb.oriachad_backend.administration.domains.dtos.AddEditDtos.AddEditAdminDto;
import com.pajiniweb.oriachad_backend.administration.domains.dtos.AdminDto;
import com.pajiniweb.oriachad_backend.administration.domains.entities.Admin;
import com.pajiniweb.oriachad_backend.administration.repositories.AdminRepository;
import com.pajiniweb.oriachad_backend.exceptions.LoginFailedException;
import com.pajiniweb.oriachad_backend.security.domain.dtos.UserLoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        LOGGER.info("Starting method getAllAdmins");
        try {
            return adminRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("Error fetching all admins", e);
            throw e;
        }
    }

    public Optional<Admin> getAdminById(Long id) {
        LOGGER.info("Starting method getAdminById with id: {}", id);
        try {
            return adminRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error("Error fetching admin by id: {}", id, e);
            throw e;
        }
    }

    public Admin createAdmin(AddEditAdminDto dto) {
        LOGGER.info("Starting method createAdmin with email: {}", dto.getEmail());
        try {
            Admin admin = Admin.builder()
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .build();
            return adminRepository.save(admin);
        } catch (Exception e) {
            LOGGER.error("Error creating admin", e);
            throw e;
        }
    }

    public Admin updateAdmin(Long id, AddEditAdminDto dto) {
        LOGGER.info("Starting method updateAdmin with id: {}", id);
        try {
            if (!adminRepository.existsById(id)) {
                LOGGER.error("Admin not found with id: {}", id);
                throw new IllegalArgumentException("Admin not found with id: " + id);
            }
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
            admin.setEmail(dto.getEmail());
            admin.setPassword(passwordEncoder.encode(dto.getPassword()));
            admin.setFirstName(dto.getFirstName());
            admin.setLastName(dto.getLastName());
            return adminRepository.save(admin);
        } catch (Exception e) {
            LOGGER.error("Error updating admin with id: {}", id, e);
            throw e;
        }
    }

    public void deleteAdmin(Long id) {
        LOGGER.info("Starting method deleteAdmin with id: {}", id);
        try {
            if (adminRepository.existsById(id)) {
                adminRepository.deleteById(id);
                LOGGER.info("Successfully deleted admin with id: {}", id);
            } else {
                LOGGER.warn("Admin not found with id: {}", id);
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting admin with id: {}", id, e);
            throw e;
        }
    }

    public Admin findByEmailAndPassword(UserLoginDto userLogInDto) {
        try {
            Optional<Admin> admin = adminRepository.findByEmail(userLogInDto.getEmail());

            if (admin.isPresent()) {
                if (passwordEncoder.matches(userLogInDto.getPassword(), admin.get().getPassword())) {
                    return admin.get();
                } else {
                    throw new LoginFailedException("error.login.fail");
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Error during login process: {}", e.getMessage());
            return null;
        }
    }

    public Admin logInByEmail(String email) throws Exception {
        return adminRepository.findByEmail(email).orElseThrow(() -> new Exception("not Found User"));
    }

    public AdminDto mapAdminEntityToDto(Admin admin) {
        return AdminDto.builder()
                .id(admin.getId())
                .email(admin.getEmail())
                .password(admin.getPassword()) // Optionally you could omit the password here for security
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .build();
    }
}
