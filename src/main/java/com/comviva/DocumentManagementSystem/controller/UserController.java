package com.comviva.DocumentManagementSystem.controller;

import java.util.Date;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.comviva.DocumentManagementSystem.bean.User;
import com.comviva.DocumentManagementSystem.service.DocumentUploadDownloadService;
import com.comviva.DocumentManagementSystem.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService us;
	@Autowired
	private DocumentUploadDownloadService docStorageService;
	public static int loginId;

	public void setUserService(UserService us) {
		this.us = us;
	}

	@GetMapping("/test")
	public String test(Model model) {
		model.addAttribute("date", new Date().toString());
		return "test";
	}

	@GetMapping("/index")
	public ModelAndView Index() {
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}

	@GetMapping("/login1")
	public ModelAndView logIn() {
		ModelAndView modelAndView = new ModelAndView("login1");
		return modelAndView;
	}

	@PostMapping("/login1")
	public ModelAndView login(@ModelAttribute("user") User user, HttpSession session) {
		User myUser = us.findByUserNameAndPassword(user.getUserName(), user.getPassword());
		loginId = myUser.getUser_id();
		// loginId=myUser.getUser_id();
		if (myUser != null) {
			session.setAttribute("loggedIn", true);
			session.setAttribute("name", myUser.getName());
			ModelAndView modelAndView = new ModelAndView("dash");
			modelAndView.addObject("iduser", "Welcome" + " " + myUser.getName() + "!");
			ModelAndView modelAndView2 = new ModelAndView("index");
			modelAndView2.addObject("msg", myUser.getUser_id());
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("login1");
			return modelAndView;
		}
	}

	@GetMapping("/register1")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView("register1");
		modelAndView.addObject("user", new User());
		return modelAndView;
	}

	@PostMapping("/register1")
	@ResponseBody
	public ModelAndView registration(@ModelAttribute("user") User user, BindingResult bindingResult) {
		System.out.println("Inside reg post   " + user.getName() + user.getUserName() + user.getPassword());
		ModelAndView modelAndView = new ModelAndView();
		User userExist = us.findByUserName(user.getUserName());
		if (userExist != null) {
			bindingResult.rejectValue("userName", "error.user", "User with the username already exists");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("register1");
		} else {
			System.out.println("Inside else");
			us.saveUserToDB(user);
			modelAndView.addObject("message", "User registration successful");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("register1");
		}
		return modelAndView;
	}

	@GetMapping("/dash")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("dash");
		return modelAndView;
	}

	@RequestMapping("/logout")
	public ModelAndView logOut(HttpSession session) {
		session.removeAttribute("loggedIn");
		session.removeAttribute("name");
		ModelAndView modelAndView = new ModelAndView("login1");
		return modelAndView;
	}

}
