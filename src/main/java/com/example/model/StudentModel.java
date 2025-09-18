package com.example.model;
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
    
    @Pattern(regexp="^[A-Za-z ]+$")
	private String sname;
    
	@Min(value=0)
	@Max(value=100)
    private Integer mark1;
	
	@Min(value=0)
	@Max(value=100)
    private Integer mark2;
    
    @Column(nullable=false, unique=true)
	@Min(value=1000, message="*Register number must be at least 4 digits")
	@Max(value=9999, message="*Register number must be at least 4 digits")
	private Integer rno;
    
    @Column(nullable=true)
   // @Column(nullable=false, length=12) //length for Bcrypt hash
    private String password;
    
    @Transient
    private Integer total;
    @Transient
    private String result;
    
    public Integer getTotal() {
    	if (mark1!=null && mark2 != null) {
    		return mark1 + mark2;
    	}
    	return 0;
    }
    
    public String getResult() {
    	if (mark1!=null && mark2 != null) {
    		if (mark1>=35 && mark2>=35) {
    			return "Pass";
    		}else {
    			return "Fail";
    		}
    	}
    	return "Not Available";
    }
    
    public Long getId() {	return id;	}
	public void setId(Long id) {	this.id = id;	}
	public String getSname() {	return sname;	}
	public void setSname(String sname) {	this.sname = sname;	}
	public Integer getMark1() {	return mark1;	}
	public void setMark1(Integer mark1) {	this.mark1 = mark1;	}
	public Integer getMark2() {	return mark2;	}
	public void setMark2(Integer mark2) {	this.mark2 = mark2;	}    
	
    public Integer getRno() {	return rno;}
	public void setRno(Integer rno) {	this.rno = rno;}
	public String getPassword() {	return password;}
	public void setPassword(String password) {	this.password = password;}
}