/*==========================
   SampleController.java
   - 사용자 정의 컨트롤러
==========================*/

package com.seolo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.seolo.personal.PersonalDTO;

@Controller
public class NavBarController
{
	@RequestMapping(value = "/nav.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String navBar(Model model, HttpServletRequest request, HttpSession session)
	{
		// 자동로그인 확인하기
		Cookie[] cookies = request.getCookies();
		
		if (cookies!=null)
		{
			PersonalDTO userLogin = new PersonalDTO();
			
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals("acNo"))
					userLogin.setAc_No(cookie.getValue());
				
				if (cookie.getName().equals("id"))
					userLogin.setPe_Id(cookie.getValue());
				
				if (cookie.getName().equals("warn"))
					userLogin.setWarningCount(Integer.parseInt(cookie.getValue()));
			}
			
			if (userLogin.getAc_No()!=null && userLogin.getPe_Id()!=null)
			{
				session.setAttribute("userLogin", userLogin);
			}
			
		}
		
		return "WEB-INF/view/MenuNavbar_new.jsp";
	}
}
