package com.example.model;

import com.example.model.StudentMarks; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "studentmarks")
public class StudentMarks {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	    
	  	@Min (1)
	  	@Max(10)
		@Digits(integer=2, fraction=0)
	    private Integer semester;
	  	
	  	@Column(nullable=false)
	  	private String subjectCode;
	  	
	    @Min(value=0)
	    @Max(value=100)
	    @Digits(integer=3, fraction=0)
	    private Integer mark;
	   		
	    @ManyToOne
	    @JoinColumn(name="student_id")
	    private StudentModel student;
	    
		/* @Transient
	    private Integer total;
	    @Transient
	    private String result;
	    public Integer getTotal() { if (mark1!=null && mark2 != null) { return mark1 + mark2;} return 0; }
	    public String getResult() {	if (mark1!=null && mark2 != null) {	if (mark1>=35 && mark2>=35) {
	    			return "Pass";}else { return "Fail";}} return "Not Available";  }*/
		
	    public Long getId() {			return id;		}
		public void setId(Long id) {			this.id = id;		}
		public Integer getSemester() {			return semester;		}
		public void setSemester(Integer semester) {	this.semester = semester;}
		public String getSubjectCode() {return subjectCode;		}
		public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;		}
		public Integer getMark() {			return mark;		}
		public void setMark(Integer mark) {	this.mark = mark;		}
}
