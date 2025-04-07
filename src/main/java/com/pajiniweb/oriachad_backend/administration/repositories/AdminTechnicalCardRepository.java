package com.pajiniweb.oriachad_backend.administration.repositories;

import com.pajiniweb.oriachad_backend.administration.domains.entities.AdminTechnicalCard;
import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AdminTechnicalCardRepository extends JpaRepository<AdminTechnicalCard, Long> {

    Optional<AdminTechnicalCard> findByCode(String code);

    @Query("SELECT tc FROM AdminTechnicalCard tc WHERE " +
            "LOWER(tc.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "AND (:idTcCategory = -1 OR tc.category.id = :idTcCategory) " +
            "AND (:month = -1 OR tc.runMonth = :month) " +
            "AND (:typeEstablishment IS NULL OR tc.typeEstablishment = :typeEstablishment)")
    Page<AdminTechnicalCard> findByCreatedBy(
            @Param("search") String search,
            @Param("idTcCategory") Long idTcCategory,
            @Param("month") int month,
            @Param("typeEstablishment") TypeEstablishment typeEstablishment,
            Pageable pageable
    );

    @Query("SELECT  MAX(tc.number) " +
            "FROM AdminTechnicalCard tc " +
            "WHERE tc.idTcCategory=:cardCategoryId " +
            "and tc.typeEstablishment=:typeEstablishment")
    Optional<Long> lastNumberOfTechnicalCard(Long cardCategoryId, TypeEstablishment typeEstablishment);

    List<AdminTechnicalCard> findByIdIn(List<Long> ids);

    List<AdminTechnicalCard> findByTypeEstablishment(TypeEstablishment typeEstablishment);
}
