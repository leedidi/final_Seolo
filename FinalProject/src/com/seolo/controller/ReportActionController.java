package com.seolo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.seolo.dto.ReportDTO;
import com.seolo.idao.IReportDAO;
import com.seolo.idao.IReportviewDAO;

@Controller
public class ReportActionController
{
	
	/// mybatis 객체 의존성 (자동) 주입~!!!
	@Autowired
	private SqlSession sqlSession;

	// 신고 처리폼 조회
	@RequestMapping(value = "/reportform.action", method = RequestMethod.POST)
	public String reportForm(Model model, HttpServletRequest request, HttpSession session)
	{
		// 로그인 안하면 차단 , url접근 시 GET방식이여서 접근 못함
		if (session.getAttribute("userLogin")==null)	
		{
			return "redirect:main.action";
		}		
		
		String checkNo = request.getParameter("checkNo");
		String acNo = request.getParameter("acNo");
		
		model.addAttribute("checkNo", checkNo);
		model.addAttribute("acNo", acNo);
		
		return "WEB-INF/view/ReprotForm.jsp";
	}
	
	@RequestMapping(value = "/report.action", method = RequestMethod.POST)
	public String report(Model model, ReportDTO dto, HttpSession session)
	{
		// 로그인 안하면 차단 , url접근 시 GET방식이여서 접근 못함
		if (session.getAttribute("userLogin")==null)	
		{
			return "redirect:main.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		if (dto.getWhy() == null)
		{
			String why = "";
			dto.setWhy(why);
		}
		
		dao.add(dto);
		
		return "WEB-INF/view/ReportFn.jsp";
	}
	
	
	
	
}
