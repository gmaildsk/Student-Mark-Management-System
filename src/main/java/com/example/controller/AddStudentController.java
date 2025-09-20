package com.example.controller;

import com.example.model.StudentMarks;
import com.example.model.StudentModel;
import com.example.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class AddStudentController {
	@Autowired
	private StudentService service;
	@GetMapping("/")
	public String welcomePage() {
	    return "index"; // your second HTML
	}

		@GetMapping("/viewHomePage")
		public String viewHomePage(Model model) {
			model.addAttribute("listStudents", service.getAllStudents());
			return "Staff-Result";
		}
		
		@GetMapping("/showNewStudentForm")
		public String showNewStudentForm(Model model) {
			
			StudentModel sm=new StudentModel();
			
			// Add 1 empty StudentMarks row to sm
			sm.getSubjects().add(new StudentMarks());
			
			model.addAttribute("smkey", sm);
			return "Add-Student";
		}
		
		@PostMapping("/addSubjectRow")
		public String addSubject(@ModelAttribute("smkey") StudentModel sm, Model model) {
			//StudentModel sm=new StudentModel();
			
			sm.getSubjects().add(new StudentMarks());
			model.addAttribute("smkey", sm);
			return "Add-Student";
		}
		
		@PostMapping("/savestudent")
		public String saveStudent(@Valid @ModelAttribute("smkey") StudentModel sm, BindingResult result, Model model) {
			
			    	//Check for validation errors(empty fields)
			    	if (result.hasErrors()) {
			    		return "Add-Student";
			    	}
			    				    	
			    	//Check for duplicate Rno
			    	if(service.isRnoExists(sm.getRno())){
			    		model.addAttribute("rnoerror", "*Register number already exists!");
			    		model.addAttribute("smkey", sm);
			    		return "Add-Student";
			    	}
			    
									
			//Preserve or assign password
			if(sm.getId() !=null) {
				StudentModel existingStudent= service.getStudentByRno(sm.getRno());

				if(existingStudent != null) {
					//preserve password
					sm.setPassword(existingStudent.getPassword());
				}else{
					//fallback for safety
					sm.setPassword("@default123");
				}
			}else {
					//new student--> assign default password
					sm.setPassword("@default123");
				}
			//save student
			service.saveStudent(sm);
			
			//Reset form and show success
			model.addAttribute("smkey", new StudentModel());
			model.addAttribute("regnewmsg", "Student registered successfully!");
			//return "redirect:/viewHomePage";
			return "Add-Student";
			
		}
}