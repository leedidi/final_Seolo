/*==========================
   SampleController.java
   - 사용자 정의 컨트롤러
==========================*/

package com.seolo.personal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class LogoutController implements Controller
{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		// 세션에 구성된 속성값들 제거
		HttpSession session = request.getSession();
		session.invalidate();
		
		// 쿠키 제거
		Cookie[] cookies = request.getCookies();
		if (cookies!=null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals("acNo"))
				{
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				
				if (cookie.getName().equals("id"))
				{
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				
				if (cookie.getName().equals("warn"))
				{
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
		
		// 로그아웃시 메인으로 돌아감
		mav.setViewName("redirect:main.action");
		
		return mav;

	}
}
