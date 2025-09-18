package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "staffmodel")
public class StaffModel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="*Staff name is required")
	private String staffname;
	
	@NotBlank(message="*Staff department is required")
	private String department;
	
	@Column(nullable=false, unique=true)
	@NotNull(message="*Staffcode is required")
	@Min(value=1000, message="*Staffcode must be 4 digits")
	@Max(value=9999, message="*Staffcode must be 4 digits")
	private Integer staffcode;
	
	@Column(nullable=false, length=60) //length for Bcrypt hash
	@NotBlank(message="*Password is required")
	@Size(min=6, message="*Password must be between 6 and 12 characters")
	private String password;
	
	public Integer getId() {	return id;	}
	public void setId(Integer id) {this.id = id;	}
	public String getStaffname() {	return staffname;	}
	public void setStaffname(String staffname) {	this.staffname = staffname;	}
	public String getDepartment() {	return department;	}
	public void setDepartment(String department) {	this.department = department;	}
	public Integer getStaffcode() {	return staffcode;	}
	public void setStaffcode(Integer staffcode) {	this.staffcode = staffcode;	}
	public String getPassword() {	return password;	}
	public void setPassword(String password) {	this.password = password;	}
}



/*package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "staffmodel")
public class StaffModel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="*Staff name is required")
	private String staffname;
	
	@NotBlank(message="*Staff department is required")
	private String department;
	
	@Column(nullable=false, unique=true)
	@NotNull(message="*Staffcode is required")
	@Min(value=1000, message="*Staffcode must be 4 digits")
	@Max(value=9999, message="*Staffcode must be 4 digits")
	private Integer staffcode;
	
	@Column(nullable=false, length=60) //length for Bcrypt hash
	@NotBlank(message="*Password is required")
	@Size(min=6, message="*Password must be atleaset  6 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character (@#$%^&+=)"
        )
	private String password;
	
	public Integer getId() {	return id;	}
	public void setId(Integer id) {this.id = id;	}
	public String getStaffname() {	return staffname;	}
	public void setStaffname(String staffname) {	this.staffname = staffname;	}
	public String getDepartment() {	return department;	}
	public void setDepartment(String department) {	this.department = department;	}
	public Integer getStaffcode() {	return staffcode;	}
	public void setStaffcode(Integer staffcode) {	this.staffcode = staffcode;	}
	public String getPassword() {	return password;	}
	public void setPassword(String password) {	this.password = password;	}
}*/