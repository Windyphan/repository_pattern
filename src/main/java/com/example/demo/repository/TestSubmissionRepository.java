package com.example.demo.repository;

import com.example.demo.entity.TestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestSubmissionRepository extends JpaRepository<TestSubmission, Long> {
    List<TestSubmission> findByStudentId(Long studentId);

}
