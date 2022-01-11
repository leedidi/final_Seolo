/*==========================
   ReportController.java
   - 사용자 정의 컨트롤러
==========================*/

package com.seolo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.seolo.admin.INoticeDAO;
import com.seolo.admin.NoticeDTO;
import com.seolo.dto.AdminDTO;
import com.seolo.dto.FaqviewDTO;
import com.seolo.dto.ReportRunDTO;
import com.seolo.dto.ReportviewDTO;
import com.seolo.idao.IAccountListDAO;
import com.seolo.idao.IFaqviewDAO;
import com.seolo.idao.IReportRunDAO;
import com.seolo.idao.IReportviewDAO;
import com.seolo.idao.IWithdrawalAccountListDAO;
import com.seolo.personal.PersonalDTO;

@Controller
public class ReportController
{
	/// mybatis 객체 의존성 (자동) 주입~!!!
	@Autowired
	private SqlSession sqlSession;
	
	/*
	// 자주 묻는 게시판 처리 게시판 조회
	// 카테고리 조회 기능 추가
	@RequestMapping(value = "/faqlist.action", method = RequestMethod.GET)
	public String faqList(FaqviewDTO dto, Model model, HttpServletRequest request)
	{
		IFaqviewDAO dao = sqlSession.getMapper(IFaqviewDAO.class);
		
		// 카테고리 숫자 받아옴
		String faq_check = request.getParameter("faq_check");
		
		// 카테고리 클릭시 카테고리 번호 넘겨주고 카테고리 출력,
		// 카테고리 미 클릭 시 전체 리스트 출력
		if(faq_check != null)
		{
			dto.setFaq_check(faq_check);
			model.addAttribute("list", dao.cateList(faq_check));
		}
		else
			model.addAttribute("list", dao.list());
		
		return "WEB-INF/view/FaqListNode.jsp";
	}
	*/
	
	
	// 신고 처리 게시판 백업
	/*
	@RequestMapping(value = "/reportlist.action", method = RequestMethod.GET)
	public String reportList(Model model)
	{
		IReportviewDAO dao = sqlSession.getMapper(IReportviewDAO.class);
		
		model.addAttribute("list", dao.list());
		
		return "WEB-INF/view/ReportCheck.jsp";
	}
	*/
	
	// 신고 처리 게시판 조회
	@RequestMapping(value = "/reportlist.action", method = RequestMethod.GET)
	public String reportList(ReportviewDTO dto, Model model, HttpServletRequest request)
	{
		IReportviewDAO dao = sqlSession.getMapper(IReportviewDAO.class);
		
		// 카테고리 이름 받아옴
		String report_check = request.getParameter("report_check");
		// 잘 받아오는지 테스트용
		//System.out.println(report_check);
		
		// 카테고리 클릭시 카테고리 번호 넘겨주고 카테고리 출력,
		// 카테고리 미 클릭 시 전체 리스트 출력
		if(report_check != null)
		{
			dto.setReport_check(report_check);
			model.addAttribute("list", dao.cateList(report_check));
			model.addAttribute("cateNameList", dao.cateNameList());
		}
		else
		{
			model.addAttribute("list", dao.list());
			model.addAttribute("cateNameList", dao.cateNameList());
		}
		
		return "WEB-INF/view/ReportCheck.jsp";
	}
	
	// 신고 처리 게시물 조회
	@RequestMapping(value = "/reportview.action", method = RequestMethod.GET)
	public String reportView(@RequestParam("rpcheck_no") int rpcheck_no, Model model)
	{
		IReportviewDAO dao = sqlSession.getMapper(IReportviewDAO.class);
		
		model.addAttribute("view", dao.view(rpcheck_no));
		
		return "WEB-INF/view/ReportSee.jsp";
	}
	
	// 신고 처리 방법 3가지
	// 신고 처리 - 신고 승인
	@RequestMapping(value = "/reportapproval.action", method = RequestMethod.GET)
	public String reportApproval(ReportRunDTO rr, HttpSession session)
	{
		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO)session.getAttribute("adminLogin");
		String ad_id = admin.getAd_Id();
		rr.setAd_id(ad_id);
		
		IReportRunDAO dao = sqlSession.getMapper(IReportRunDAO.class);
		
		dao.approval(rr);

		return "redirect:reportlist.action";
	}
	
	// 신고 처리 - 신고 반려
	@RequestMapping(value = "/reportreject.action", method = RequestMethod.GET)
	public String reportReject(ReportRunDTO rr, HttpSession session)
	{
		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO)session.getAttribute("adminLogin");
		String ad_id = admin.getAd_Id();
		rr.setAd_id(ad_id);
		
		IReportRunDAO dao = sqlSession.getMapper(IReportRunDAO.class);
			
		dao.reject(rr);
		
		return "redirect:reportlist.action";
	}
	
	// 신고 처리 - 허위 신고
	@RequestMapping(value = "/reportrefake.action", method = RequestMethod.GET)
	public String reportFake(ReportRunDTO rr, HttpSession session)
	{
		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO)session.getAttribute("adminLogin");
		String ad_id = admin.getAd_Id();
		rr.setAd_id(ad_id);
		
		IReportRunDAO dao = sqlSession.getMapper(IReportRunDAO.class);

		dao.fake(rr);
		
		return "redirect:reportlist.action";
	}
	
	
}
