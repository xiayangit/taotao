package com.xy.taotao.sso.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.sso.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public Object login(String username,String password,HttpServletResponse response) {
		TaoTaoResult login = userService.login(username, password);
		if(login.getStatus().equals(200)) {
			Cookie c = new Cookie("token", login.getData().toString());
			//c.setDomain(".test.com");
			response.addCookie(c);
		}
		Cookie c = new Cookie("token", login.getData().toString());
		c.setPath("/");
		//c.setDomain(".test.com");
		response.addCookie(c);
		return login;
	}
	
	@RequestMapping(value="/token/{token}",method=RequestMethod.GET)
	@ResponseBody
	public Object getUesr(@PathVariable String token) {
		TaoTaoResult userByToken = userService.getUserByToken(token);
		return userByToken;
	}
	
}
