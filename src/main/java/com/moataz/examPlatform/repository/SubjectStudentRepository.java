package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.SubjectStudentId;
import com.moataz.examPlatform.model.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectStudentRepository extends JpaRepository<UserSubject, SubjectStudentId> {

}
