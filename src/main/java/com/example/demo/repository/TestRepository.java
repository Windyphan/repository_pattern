package com.example.demo.repository;

import com.example.demo.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findByTestId(Long id);
    @Query("SELECT DISTINCT t FROM Test t JOIN FETCH t.questions q JOIN FETCH q.answers a WHERE t.testId = :testId")
    Test findTestChildEntitiesByTestId(@Param("testId") Long testId);
}
