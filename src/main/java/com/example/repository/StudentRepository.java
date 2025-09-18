package com.example.repository;

import com.example.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StudentRepository extends JpaRepository<StudentModel, Long>{
	@Transactional
	@Modifying
	@Query("UPDATE StudentModel s SET s.password=:password WHERE s.rno=:rno")
	int updatePasswordByRno(@Param("rno") Integer rno,@Param("password")  String password);
	StudentModel findByRno(Integer rno);
}