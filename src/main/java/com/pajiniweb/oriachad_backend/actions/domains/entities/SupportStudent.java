package com.pajiniweb.oriachad_backend.actions.domains.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "support_student")
@EntityListeners(AuditingEntityListener.class)
public class SupportStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;
    @ManyToMany(mappedBy = "supportStudents", fetch = FetchType.LAZY)
    private List<ShedSetting> shedSettings;
}
