package com.example.controller;

import com.example.service.StaffService;
import com.example.service.StudentService;
import com.example.model.StaffModel;
import com.example.model.StudentModel;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class StaffLoginController {
    @Autowired
    private StaffService service;  // ✅ inject service
    
    @Autowired
	private StudentService studentService;

    //Registration page
    @GetMapping("/staff/register")
    public String showRegisterPage(Model model) {
    	model.addAttribute("staff", new StaffModel());
    	return "Staff-register";
    }
    
    @PostMapping("/staff/register")
    public String register(@Valid @ModelAttribute("staff") StaffModel sm, BindingResult result, Model model) {
    	
    	//Check for validation errors(empty fields)
    	if (result.hasErrors()) {
    		return "Staff-register";//return to registartion page & will show only unfulfilled field errors
    	}
    	
    	//Check for duplicate staffcode
    	if(service.isStaffcodeExists(sm.getStaffcode())){
    		model.addAttribute("staffcodeerror", "Staff code already exists!");
    		return "Staff-register";
    	}
    	service.registerStaff(sm);
    	/*redirectAttrs.addFlashAttribute("regnewmsg", "Staff registered successfully!");
    	return "redirect:/stafflogin"; // stay on page for now to show success*/
    	
		model.addAttribute("regnewmsg", "Staff registered successfully!");
		
		//Clear the staff object so fields in login form are empty
		model.addAttribute("staff", new StaffModel());
		
		return "Stafflogin";
    }
    
    
    //Password update page
    @GetMapping("/update-password")
    public String showUpdatePage(Model model) {
    	model.addAttribute("staff", new StaffModel());
    	return "staff-password-update";
    }
    
    @PostMapping("/update-password")
    public String setPassword(@RequestParam Integer staffcode, @RequestParam String password, Model model) {
    	boolean success=service.setPassword(staffcode, password);
    	if(success) {
			model.addAttribute("Passwordsuccess", "Password updated successfully!");
			model.addAttribute("staff", new StaffModel()); //fresh login form
    		return "Stafflogin"; // go to login page
    	} else {
    		model.addAttribute("Passwordfailure", "Staff with staffcode "+ staffcode +" not found!");
    		return "staff-password-update"; //stay on same page
    	}
    }
    
    
    //Login page
    @GetMapping("/stafflogin")
    public String showLoginPage(Model model) {
    	if(!model.containsAttribute("staff")) {
    	model.addAttribute("staff", new StaffModel());
    	}
    	return "Stafflogin";
    }
    
    @PostMapping("/stafflogin")
    public String login(@ModelAttribute("staff") StaffModel sm, BindingResult result, Model model) {
    	    	
    	if(result.hasErrors()) {
    		return "Stafflogin";
    	}
    	StaffModel auth= service.authenticate(sm.getStaffcode(), sm.getPassword());
    	if (auth!=null) {
    		//Staff details
    		model.addAttribute("staffname", auth.getStaffname());
    		model.addAttribute("staffcode", auth.getStaffcode());
    		
    		//Load all students
    		    		List<StudentModel> listStudents= studentService.getAllStudents();
    		model.addAttribute("listStudents", listStudents);
    		
    		//Go to res page
    		return "Staff-Result";
    	}else {
    		model.addAttribute("error", "Invalid credentials");
    		return "Stafflogin";
    	}
    }
    
    //Logout page
    @GetMapping("/stafflogout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
    	//Invalidate the session to remove all attributes
    	session.invalidate();
    	redirectAttributes.addFlashAttribute("logoutmsg", "You have been logged out successfully.");
    	return "redirect:/stafflogin";
    }
    
    @GetMapping("/home")
    public String gohome() {
    	return "index";
    }
}


/*package com.example.controller;

import com.example.service.StaffService;
import com.example.service.StudentService;
import com.example.model.StaffModel;
import com.example.model.StudentModel;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class StaffLoginController {
    @Autowired
    private StaffService service;  // ✅ inject service
    
    @Autowired
	private StudentService studentService;
    
    //@Autowired
   // private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder;
    
    //Registration page
    @GetMapping("/staff/register")
    public String showRegisterPage(Model model) {
    	model.addAttribute("staff", new StaffModel());
    	return "Staff-register";
    }
    
    @PostMapping("/staff/register")
    public String register(@Valid @ModelAttribute("staff") StaffModel sm, BindingResult result, Model model) {
    	
    	//Check for validation errors(empty fields)
    	if (result.hasErrors()) {
    		return "Staff-register";//return to registartion page & will show only unfulfilled field errors
    	}
 	
    	//Check for duplicate staffcode
    	if(service.isStaffcodeExists(sm.getStaffcode())){
    		model.addAttribute("staffcodeerror", "Staff code already exists!");
    		return "Staff-register";
    	}
    	service.registerStaff(sm);
		model.addAttribute("regnewmsg", "Staff registered successfully!");
		
		//Clear the staff object so fields in login form are empty
		model.addAttribute("staff", new StaffModel());
		
		return "Stafflogin";
    }
     
    //Password update page
    @GetMapping("/update-password")
    public String showUpdatePage(Model model) {
    	model.addAttribute("staff", new StaffModel());
    	return "staff-password-update";
    }
    
    @PostMapping("/update-password")
 
  public String setPassword(@Valid @ModelAttribute("staff") StaffModel staff, BindingResult result, Model model) {
    	if(result.hasErrors()) {
    	    return "staff-password-update";
    	   }
    	//Encode password before saving
    	//String encoded= passwordEncoder.encode(staff.getPassword());
    	    	
    	boolean success=service.setPassword(staff.getStaffcode(), staff.getPassword());//raw password
    	if(success) {
			model.addAttribute("Passwordsuccess", "Password updated successfully!");
			model.addAttribute("staff", new StaffModel()); //fresh login form
			return "Stafflogin";
			//return "Stafflogin"; // go to login page
    	} else {
    		model.addAttribute("Passwordfailure", "Staff with staffcode "+ staff.getStaffcode() +" not found!");
    		return "staff-password-update"; //stay on same page
    	}
    }
    
    //Login page
    @GetMapping("/stafflogin")
    public String showLoginPage(Model model) {
    	if(!model.containsAttribute("staff")) {
    	model.addAttribute("staff", new StaffModel());
    	}
    	return "Stafflogin";
    }
    
    @PostMapping("/stafflogin")
    public String login(@Valid @ModelAttribute("staff") StaffModel sm, BindingResult result, Model model) {
    	    	
    	if(result.hasErrors()) {
    		return "Stafflogin";
    	}
    	
    	StaffModel auth= service.authenticate(sm.getStaffcode(), sm.getPassword());
    		
    	if (auth!=null) {
    		//Staff details
    		model.addAttribute("staffname", auth.getStaffname());
    		model.addAttribute("staffcode", auth.getStaffcode());
    		
    		//Load all students
    		    		List<StudentModel> listStudents= studentService.getAllStudents();
    		model.addAttribute("listStudents", listStudents);
    		
    		//Go to res page
    		return "Staff-Result";
    	}else {
    		model.addAttribute("error", "Invalid credentials");
    		return "Stafflogin";
    	}
    }
    
    //Logout page
    @GetMapping("/stafflogout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
    	//Invalidate the session to remove all attributes
    	session.invalidate();
    	redirectAttributes.addFlashAttribute("logoutmsg", "You have been logged out successfully.");
    	return "redirect:/stafflogin";
    }
    
    @GetMapping("/home")
    public String gohome() {
    	return "index";
    }
}*/