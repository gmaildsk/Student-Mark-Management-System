package com.example.model;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "studentmodel")
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false, unique=true)
	@Min(value=1000, message="*Register number must be at least 4 digits")
	@Max(value=9999, message="*Register number must be at least 4 digits")
	private Integer rno;
    
    @Pattern(regexp="^[A-Za-z ]+$")
	private String sname;
    
    @Column(nullable=true)
   // @Column(nullable=false, length=12) //length for Bcrypt hash
    private String password;
    
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="student_id")
    private List<StudentMarks> subjects=new ArrayList<>();
     
	public Long getId() {	return id;	}
	public void setId(Long id) {	this.id = id;	}
	public String getSname() {	return sname;	}
	public void setSname(String sname) {	this.sname = sname;	}   
	
    public Integer getRno() {	return rno;}
	public void setRno(Integer rno) {	this.rno = rno;}
	public String getPassword() {	return password;}
	public void setPassword(String password) {	this.password = password;}
	
    public List<StudentMarks> getSubjects() {	return subjects;	}
	public void setSubjects(List<StudentMarks> subjects) {	this.subjects = subjects;	}
}