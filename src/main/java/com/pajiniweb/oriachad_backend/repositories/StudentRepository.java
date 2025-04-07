package com.pajiniweb.oriachad_backend.repositories;

import com.pajiniweb.oriachad_backend.domains.dtos.StudentDesireDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentNoteDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentWithDesireDto;
import com.pajiniweb.oriachad_backend.domains.entities.Student;
import org.apache.commons.lang3.concurrent.UncheckedFuture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {




    @Query("select s from Student s   " +
            "LEFT JOIN StudentClasse sc ON s.id = sc.idStudent " +
            "where  sc.idClasse = :idClasse  " +
            "and  sc.removed = false " +
            "AND (:search IS NULL OR LOWER(CONCAT(s.firstName,' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))" +
            "ORDER BY s.firstName, s.lastName" )
    Page<Student> search(@Param("idClasse") Long idClasse,
                         @Param("search") String search,
                         Pageable pageable);


    Optional<Student> findByNbrRakmanaAndIdEstablishment(String nbrRakmana,Long idEstablishment);

    @Query("select s from Student s " +
            "left join s.studentClasses sc " +
            "where (concat(s.firstName,' ',s.lastName) like concat('%', ?1, '%') " +
            "or concat(s.lastName,' ',s.firstName) like concat('%', ?1, '%')) " +
            "and sc.idClasse=?3 and s.birthDate =?2 " +
            "and  sc.removed = false " )
    Optional<Student> findByFullNameAndBirthDate(String lastName, LocalDate birthdate,Long idClass);

    @Query("SELECT new com.pajiniweb.oriachad_backend.domains.dtos.StudentNoteDto(s, CASE WHEN a.id IS NOT NULL THEN true ELSE false END) " +
            "FROM Student s " +
            "JOIN s.studentClasses sc " +
            "LEFT JOIN Average a ON s.id = a.idStudent AND a.idTrimestre = :idTrimestre " +
            "WHERE sc.idClasse = :idClasse " +
            "AND (:search IS NULL OR LOWER(CONCAT(s.firstName,' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "and  sc.removed = false " +
            "ORDER BY s.firstName, s.lastName")
    Page<StudentNoteDto> searchStudentsWithStatusByTrimestreAndClass(@Param("idClasse") Long idClasse,
                                                                     @Param("idTrimestre") Long idTrimestre,
                                                                     @Param("search") String search,
                                                                     Pageable pageable);


    @Query("select count(s) from Student as s " +
            "left join s.studentClasses sc " +
            "LEFT JOIN sc.classe c " +
            "where c.idYear=:idYear " +
            "and c.idLevel=:idLevel " +
            "and  sc.removed = false " +
            "and c.idEstablishment=:idEstablishment")
    int  countByLevelAndYearAndEstablishment(@Param("idLevel")Long idLevel,@Param("idYear")Long idYear,@Param("idEstablishment")Long idEstablishment);
    @Query("select count(s) from Student as s " +
            "left join s.studentClasses sc " +
            "LEFT JOIN sc.classe c " +
            "where c.idYear=:idYear " +
            "and  sc.removed = false " +
            "and c.idSpeciality=:idSpeciality " +
            "and c.idEstablishment=:idEstablishment")
    int  countBySpecialityAndYearAndEstablishment(@Param("idSpeciality")Long idSpeciality,@Param("idYear")Long idYear,@Param("idEstablishment")Long idEstablishment);
    @Query("select count(s) from Student as s " +
            "left join s.studentClasses sc " +
            "where sc.idClasse=:idClass"+
            " and  sc.removed = false " )
    int  countByClasses(@Param("idClass")Long idClass);

    @Query("select s from Student as s " +
            "left join s.infosStudent info " +
            "left JOIN s.studentClasses sc " +
            "where sc.classe.idYear=:idYear " +
            "and info.isDisease=true " +
            " and  sc.removed = false "+
            " and sc.classe.idEstablishment=:idEstablishment")
    List<Student>  getAllByDiseases(Long idYear,Long idEstablishment);
    @Query("select s from Student as s " +
            "left join s.infosStudent info " +
            "left JOIN s.studentClasses sc " +
            "where sc.classe.idYear=:idYear" +
            " and  sc.removed = false "+
            " and info.isNeed=true " +
            " and sc.classe.idEstablishment=:idEstablishment")
    List<Student>  getAllByNeeds(Long idYear,Long idEstablishment);
    @Query("select s from Student as s " +
            "left join s.infosStudent info " +
            "left JOIN s.studentClasses sc " +
            "where sc.classe.idYear=:idYear " +
            " and  sc.removed = false "+
            "and info.isMotherOrphan=true or info.isFatherOrphan=true  and sc.classe.idEstablishment=:idEstablishment")
    List<Student> getAllByOrphans(Long idYear,Long idEstablishment);
    @Query("select s from Student as s " +
            "left join s.infosStudent info " +
            "left JOIN s.studentClasses sc " +
            "where sc.classe.idYear=:idYear" +
            " and  sc.removed = false " +
            " and  info.isMain=true   and sc.classe.idEstablishment=:idEstablishment")
    List<Student> getAllByMains(Long idYear,Long idEstablishment);

    @Query("select s from Student as s " +
            "left join s.studentClasses sc " +
            "where sc.classe.year.current=true " +
            " and  sc.removed = false "
            )
    List<Student> getStudentByCurrentYear();
    @Query("select s from Student s   " +
            "LEFT JOIN StudentClasse sc ON s.id = sc.idStudent " +
            "where  sc.removed = false and  sc.classe.year.current=true  " +
            "AND (:search IS NULL OR LOWER(CONCAT(s.firstName,' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))" +
            "and sc.classe.idEstablishment=:idEstablishment ORDER BY s.firstName, s.lastName" )
    Page<Student> searchCurrentYear( @Param("search") String search,Long idEstablishment,
                         Pageable pageable);
    @Query("select s from Student as s " +
            "left JOIN s.studentClasses sc " +
            "where sc.idClasse=:idClass" )
	List<Student> getAllStudentByClassId(Long idClass);
    @Query("select s from Student as s " +
            "left JOIN s.studentClasses sc " +
            "where sc.classe.idLevel=:idLevel " +
            "and  sc.classe.idYear=:idYear" )
    List<Student> getAllStudentByLevel(Long idLevel,Long idYear);
    @Query("select s from Student as s " +
            "left JOIN s.studentClasses sc " +
            "where sc.classe.idSpeciality=:idSpeciality " +
            "and  sc.classe.idYear=:idYear" )
    List<Student> getAllStudentBySpeciality(Long idSpeciality,Long idYear);
    @Query("SELECT new com.pajiniweb.oriachad_backend.domains.dtos.StudentDesireDto(s, CASE WHEN d.id IS NOT NULL THEN true ELSE false END) " +
            "FROM Student s " +
            "JOIN s.studentClasses sc " +
            "LEFT JOIN Desire d ON s.id = d.idStudent and d.idYear=:idYear  " +
            "WHERE sc.idClasse = :idClasse " +
            "AND (:search IS NULL OR LOWER(CONCAT(s.firstName,' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "and  sc.removed = false " +
            "ORDER BY s.firstName, s.lastName")
    Page<StudentDesireDto> searchWithDesire(Long idClasse, Long idYear , String search, Pageable pageable);

    @Query("SELECT new com.pajiniweb.oriachad_backend.domains.dtos.StudentWithDesireDto(s, d) " +
            "FROM Student s " +
            "JOIN s.studentClasses sc " +
            "LEFT JOIN Desire d ON s.id = d.idStudent and d.idYear=:idYear " +
            "WHERE sc.idClasse = :idClasse " +
            "and  sc.removed = false " +
            "ORDER BY s.firstName, s.lastName")
    Page<StudentWithDesireDto> allWithDesire(Long idClasse, Long idYear, Pageable pageable);
    @Query("SELECT new com.pajiniweb.oriachad_backend.domains.dtos.StudentWithDesireDto(s, d) " +
            "FROM Student s " +
            "LEFT JOIN Desire d ON s.id = d.idStudent AND d.idYear = :idYear " +
            "WHERE s.id = :idStudent")
    StudentWithDesireDto oneWithDesire(@Param("idYear") Long idYear, @Param("idStudent") Long idStudent);
}
