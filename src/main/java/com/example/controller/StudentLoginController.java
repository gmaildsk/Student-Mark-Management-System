package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentLoginController {
    @Autowired
    private StudentService service;  // ✅ inject service

    //Password registration page
    @GetMapping("/set-password")
    public String showRegisterPage(Model model) {
    	model.addAttribute("stud", new StudentModel());
    	return "Student-Registration";
    }
    
    @PostMapping("/set-password")
    //@ResponseBody
    public String setPassword(@RequestParam Integer rno, @RequestParam String password, Model model) {
    	boolean success=service.setPassword(rno, password);
    	if(success) {
			model.addAttribute("Passwordsuccess", "Password updated successfully!");
			model.addAttribute("stud", new StudentModel()); //fresh login form
    		return "Studentlogin"; // go to login page
    	} else {
    		model.addAttribute("Passwordfailure", "Student with register number "+ rno +" not found!");
    		return "Student-Registration"; //stay on same page
    	}
    }
    //-----------------------------------------------
	
	//@Autowired
	//private StudentService service;
	
	@GetMapping("/studentlogin")
	public String showLoginPage(Model model) {
		model.addAttribute("stud", new StudentModel());
		return "Studentlogin";
	}
	
	@PostMapping("/studentlogin")
	public String processLogin(@ModelAttribute("stud") StudentModel sm, Model model) {
		
		StudentModel auth=service.authenticate(sm.getRno(), sm.getPassword());
		
		if(auth!=null) {
			model.addAttribute("heyman", auth);// send student data to result page
			return "Student-Result";
		}else {
			model.addAttribute("error", "Invalid credential");
			return "Studentlogin";
		}
	}
}