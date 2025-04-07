package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<OriachadUser, Long> {

    @Query("SELECT u FROM OriachadUser u WHERE u.email = :email")
    Optional<OriachadUser> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("update OriachadUser u set u.password = ?2 where u.email = ?1")
    void updatePassword(String email, String password);

    @Query("SELECT u FROM  OriachadUser u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<OriachadUser> search(Pageable pageable , @Param("search") String search);

    @Query("SELECT u FROM OriachadUser u WHERE u.establishment.type = :type")
    List<OriachadUser> getAllByEstablishmentType(@Param("type") TypeEstablishment type);

}