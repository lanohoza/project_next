package com.pajiniweb.oriachad_backend.administration.repositories;

import com.pajiniweb.oriachad_backend.administration.domains.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    Optional<Admin> findByEmail(String email);
}