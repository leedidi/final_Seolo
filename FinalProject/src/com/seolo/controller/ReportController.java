/* ==========================
 * ReportController.java
 * - 사용자 정의 컨트롤러
 * ========================== */

package com.seolo.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.seolo.dto.AdminDTO;
import com.seolo.dto.ReportRunDTO;
import com.seolo.dto.ReportviewDTO;
import com.seolo.idao.IReportRunDAO;
import com.seolo.idao.IReportviewDAO;

@Controller
public class ReportController
{
	@Autowired
	private SqlSession sqlSession;

	// 신고 처리 게시판 조회
	@RequestMapping(value = "/reportlist.action", method = RequestMethod.GET)
	public String reportList(HttpSession session, Model model, ReportviewDTO dto, String pageNum, String report_check)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		//○ 주요 기능 실행
		IReportviewDAO dao = sqlSession.getMapper(IReportviewDAO.class);
		MyUtil my = new MyUtil();

		//① 현재 표시되어야 하는 페이지
		int currentPage = (pageNum != null) ? Integer.parseInt(pageNum) : 1;

		//② 한 페이지에 표시할 데이터 갯수		
		int numPerPage = 10;

		//③ 게시글 목록 조회: 가져올 게시글(RNUM)의 시작 번호, 끝 번호
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		//④ 페이징 처리 관련 주요 변수 선언
		int dataCount, totalPage;			//-- 전체 데이터 갯수, 전체 페이지 갯수
		String listUrl, pageIndexList;		//-- url, 페이징 처리

		//⑤ 게시글 목록, 페이징 처리
		if(report_check != null)
		{
			//○ 카테고리 선택 시(report_check(카테고리 번호) 값 존재)
			//　 → url 에 카테고리 정보 포함, 카테고리와 관련된 리스트 출력 

			//-- 전체 데이터 갯수, 전체 페이지 갯수
			dataCount = dao.cateCount(report_check);
			totalPage = my.getPageCount(numPerPage, dataCount);

			//-- url, 페이징 처리
			listUrl = "reportlist.action?report_check=" + report_check;
			pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

			//-- 카테고리 선택 시 게시글 목록
			dto.setReport_check(report_check);
			model.addAttribute("list", dao.cateList(start, end, report_check));
		}
		else
		{
			//○ 카테고리 미 선택 시(report_check(카테고리 번호) 값 존재 Ｘ)
			//　 → 전체 리스트 출력

			//-- 전체 데이터 갯수, 전체 페이지 갯수
			dataCount = dao.count();
			totalPage = my.getPageCount(numPerPage, dataCount);

			//-- url, 페이징 처리
			listUrl = "reportlist.action";
			pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

			//-- 게시글 목록
			model.addAttribute("list", dao.list(start, end));
		}

		model.addAttribute("pageIndexList", pageIndexList);		//-- 페이징 처리된 문자열
		model.addAttribute("cateNameList", dao.cateNameList());	//-- 카테고리 목록

		return "WEB-INF/view/ReportCheck.jsp";
	}

	// 신고 처리 게시물 조회
	@RequestMapping(value = "/reportview.action", method = RequestMethod.GET)
	public String reportView(HttpSession session, @RequestParam("rpcheck_no") int rpcheck_no, Model model)
	{
		IReportviewDAO dao = sqlSession.getMapper(IReportviewDAO.class);

		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		//○ 주요 기능 실행
		model.addAttribute("view", dao.view(rpcheck_no));

		return "WEB-INF/view/ReportSee.jsp";
	}

	// 신고 처리 방법 3가지
	// 신고 처리 - 신고 승인
	@RequestMapping(value = "/reportapproval.action", method = RequestMethod.GET)
	public String reportApproval(ReportRunDTO rr, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO) session.getAttribute("adminLogin");
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
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO) session.getAttribute("adminLogin");
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
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO) session.getAttribute("adminLogin");
		String ad_id = admin.getAd_Id();
		rr.setAd_id(ad_id);

		IReportRunDAO dao = sqlSession.getMapper(IReportRunDAO.class);
		dao.fake(rr);

		return "redirect:reportlist.action";
	}

}
