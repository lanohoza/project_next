package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.ForgetPassword;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, Long> {

    @Query("SELECT fp FROM ForgetPassword fp WHERE fp.otp = ?1 AND fp.User = ?2")
    Optional<ForgetPassword> findByOtpAndUser(Integer otp, OriachadUser User);
}