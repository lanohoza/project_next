package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.entities.EstablishmentEmployees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstablishmentEmployeesRepository extends JpaRepository<EstablishmentEmployees, Long> {

    Page<EstablishmentEmployees> findByIdCreatedBy(Long createdById, Pageable pageable);

    // For create scenario
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    // For update scenario: Check duplicate excluding the current entity
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    @Query("SELECT ee FROM EstablishmentEmployees ee WHERE ee.idCreatedBy = :idUser " +
            "AND (LOWER(ee.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(ee.lastName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(ee.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(ee.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(ee.type) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<EstablishmentEmployees> search(@Param("idUser") Long idUser,
                                                      @Param("search") String search,
                                                      Pageable pageable);
}
