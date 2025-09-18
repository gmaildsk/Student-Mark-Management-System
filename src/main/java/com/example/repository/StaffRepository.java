package com.example.repository;

import com.example.model.StaffModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StaffRepository extends JpaRepository<StaffModel, Integer> {
	//StaffModel findByStaffcodeAndPassword(Integer staffcode, String password);
	//StaffModel findByStaffcode(Integer staffcode);
	
	@Transactional
	@Modifying
	@Query("UPDATE StaffModel s SET s.password=:password WHERE s.staffcode=:staffcode")
	int updatePasswordByStaffcode(@Param("staffcode") Integer staffcode,@Param("password")  String password);
	StaffModel findByStaffcode(Integer staffcode);
}



/*package com.example.repository;

import com.example.model.StaffModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StaffRepository extends JpaRepository<StaffModel, Integer> {
	//StaffModel findByStaffcodeAndPassword(Integer staffcode, String password);
	//StaffModel findByStaffcode(Integer staffcode);
	
	@Transactional
	@Modifying
	@Query("UPDATE StaffModel s SET s.password=:password WHERE s.staffcode=:staffcode")
	int updatePasswordByStaffcode(@Param("staffcode") Integer staffcode,@Param("password")  String password);
	StaffModel findByStaffcode(Integer staffcode);
}*/