package com.example.service;

import com.example.model.StudentModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository repo;
	
	private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
	
	//method for login authentication
	public StudentModel authenticate(Integer rno, String rawPassword) {
		StudentModel sm=repo.findByRno(rno);
		if(sm!=null && sm.getPassword() !=null) {
			boolean match=passwordEncoder.matches(rawPassword, sm.getPassword());
					if(match) {
						return sm; //login success
					}
			}
		return null; // login failed
	}
	
	public StudentModel getStudentByRno(Integer rno) {
		return repo.findByRno(rno); //uses your custom finder
	}
	
	public boolean setPassword(Integer rno, String password) {
		String encoded= passwordEncoder.encode(password);
		int updated=repo.updatePasswordByRno(rno, encoded);// store encoded password
		return updated>0;// true if success
	}
	
	public StudentModel findByRno(Integer rno) {
		return repo.findByRno(rno);
	}
	
	public void saveStudent(StudentModel sm) {
		//always encode password before save
		if(sm.getPassword() !=null) {
			sm.setPassword(passwordEncoder.encode(sm.getPassword()));
		}
		repo.save(sm);
	}
	
	public List<StudentModel> getAllStudents(){
		return repo.findAll();
	}
	//check if staff code already exists
	public boolean isRnoExists(Integer rno) {
		return repo.findByRno(rno)!=null;
	}
}