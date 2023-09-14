package edu.poly.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.dao.BookticketsDAO;
import edu.poly.dao.UserDAO;
import edu.poly.entity.Account;
import edu.poly.entity.Booktickets;
import edu.poly.entity.Bookticketsdetail;
import edu.poly.service.UserService;
import io.micrometer.core.instrument.MultiGauge.Row;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	HttpSession session;
	@Autowired
	UserDAO userDAO;
	@Autowired
	BookticketsDAO bookticketsDAO;
	@Autowired
	UserService userService;

	// to login page
	@RequestMapping("/user/login")
	public String login(Model model, HttpServletRequest request) {
		// Kiểm tra xem cookie có tồn tại hay không
		String savedUsername = null;
		String savedPassword = null;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					savedUsername = cookie.getValue();
				} else if (cookie.getName().equals("password")) {
					savedPassword = cookie.getValue();
				}
			}
		}

		// Kiểm tra xem thông tin tài khoản đã lưu trong cookie chưa
		if (savedUsername != null && savedPassword != null) {
			// Có thông tin tài khoản trong cookie, gửi tài khoản vào model
			model.addAttribute("savedUsername", savedUsername);
			model.addAttribute("savedPassword", savedPassword);
		}
		addParam(model);
		return "users/element/login";
	}

	// to register page
	@RequestMapping("/user/register")
	public String rigister(Model model) {
		// model.addAttribute("erusername", "Check username");
		Account account = new Account();
		addParam(model);
		model.addAttribute("ac", account);
		return "users/element/register";
	}

	// to forgotpassword page
	@RequestMapping("/user/forgotpassword")
	public String forgotpassword(Model model) {
		addParam(model);
		return "users/element/forgotpassword";
	}

	// to save register account
	@PostMapping("/account/save")
	public String save(Model model, @Validated @ModelAttribute("ac") Account form, Errors error) {
		if (error.hasErrors()) {
			// model.addAttribute("message","Vui lòng sửa các lỗi sau:");
			addParam(model);

			return "users/element/register";

		}
		try {
			Account account = userDAO.findById(form.getId()).get();
			model.addAttribute("ac", form);
			model.addAttribute("acalreadyexists", "Account already exists");
			return "users/element/register";

		} catch (Exception e) {
			// TODO: handle exception
			userDAO.save(form);

			model.addAttribute("registersuccess", "Register success. Login right now!");
			return "users/element/login";
		}
	}

	// to save change information form user
	@PostMapping("/account/savechange")
	public String saveChang(Model model, @Validated @ModelAttribute("ac") Account form, Errors error) {
		if (error.hasErrors()) {
			// model.addAttribute("message","Vui lòng sửa các lỗi sau:");
			model.addAttribute("er", "Please enter full information");
		}
		userDAO.save(form);
		model.addAttribute("ac", form);
		model.addAttribute("islogin", true);
		model.addAttribute("yourname", form.getName());
		model.addAttribute("id", form.getId());
		return "users/userinformation";
	}

	// to profile
	@RequestMapping("/user/information")
	private String yourInformation(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();
		Account account = userDAO.findById(id).get();
		model.addAttribute("ac", account);
		model.addAttribute("islogin", true);
		model.addAttribute("yourname", account.getName());
		model.addAttribute("id", account.getId());
		return "users/userinformation";
	}

	// to history
	@RequestMapping("/user/history")
	private String yourHistory(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();
		List<Booktickets> list = bookticketsDAO.findByUserId(id);
		model.addAttribute("list", list);
		return "users/history";
	}

	// pram for nav
	public void addParam(Model model) {
		model.addAttribute("id", "");
		model.addAttribute("islogin", false);
		model.addAttribute("yourname", "Tài khoản");

	}
}
