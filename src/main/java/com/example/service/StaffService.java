package com.example.service;

import com.example.model.StaffModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.repository.StaffRepository;

@Service
public class StaffService {
	@Autowired
	private StaffRepository repo;
	
	private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
	
	//method for login authentication
	public StaffModel authenticate(Integer staffcode, String rawPassword) {
		StaffModel sm= repo.findByStaffcode(staffcode);
		if(sm!=null && passwordEncoder.matches(rawPassword, sm.getPassword())) {
			return sm; //login success
			}
		return null; // login failed
	}
	
	
	//check if staff code already exists
	public boolean isStaffcodeExists(Integer staffcode) {
		return repo.findByStaffcode(staffcode)!=null;
	}
	
	//Register new staff with encoded password
	public StaffModel registerStaff(StaffModel sm) {
		sm.setPassword(passwordEncoder.encode(sm.getPassword())); //hash before saving
		return repo.save(sm);
	}
	public boolean setPassword(Integer staffcode, String password) {
		String encoded= passwordEncoder.encode(password);
		int updated=repo.updatePasswordByStaffcode(staffcode, encoded);// store encoded password
		return updated>0;// true if success
	}
}





/*package com.example.service;

import com.example.model.StaffModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.repository.StaffRepository;

@Service
public class StaffService {
	@Autowired
	private StaffRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
	
	//method for login authentication
	public StaffModel authenticate(Integer staffcode, String rawPassword) {
		StaffModel sm= repo.findByStaffcode(staffcode);
		if(sm!=null && passwordEncoder.matches(rawPassword, sm.getPassword())) {
			return sm; //login success
			}
		return null; // login failed
	}
	
	//check if staff code already exists
	public boolean isStaffcodeExists(Integer staffcode) {
		return repo.findByStaffcode(staffcode)!=null;
	}
	
	//Register new staff with encoded password
	public StaffModel registerStaff(StaffModel sm) {
		sm.setPassword(passwordEncoder.encode(sm.getPassword())); //hash before saving
		return repo.save(sm);
	}
	public boolean setPassword(Integer staffcode, String rawPassword) {

		String encoded= passwordEncoder.encode(rawPassword);
		int updated=repo.updatePasswordByStaffcode(staffcode, encoded);// store encoded password
		return updated>0;// true if success
	}	
}*/