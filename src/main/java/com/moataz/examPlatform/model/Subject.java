package com.moataz.examPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID subjectId;

    private String name;

    private Integer level;
    private Integer semester;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private Set<UserSubject> userSubjects = new HashSet<>();

    @OneToMany(mappedBy = "subject", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Set<Exam> exams = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
