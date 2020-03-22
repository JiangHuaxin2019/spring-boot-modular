package com.jhxapi.web.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		request.getSession();
		return "user/login";
	}

}
