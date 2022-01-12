/* ==========================
 * AccountListController.java
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

import com.seolo.idao.IAccountListDAO;
import com.seolo.idao.IWithdrawalAccountListDAO;

@Controller
public class AccountListController
{
	@Autowired
	private SqlSession sqlSession;

	// 현재 회원 리스트 조회
	@RequestMapping(value = "/accountlist.action", method = RequestMethod.GET)
	public String accountList(Model model, HttpSession session, String pageNum)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		IAccountListDAO dao = sqlSession.getMapper(IAccountListDAO.class);
		MyUtil my = new MyUtil();

		//① 현재 표시되어야 하는 페이지
		int currentPage = (pageNum != null) ? Integer.parseInt(pageNum) : 1;

		//② 전체 페이지를 기준으로 총 페이지 수 계산
		int numPerPage = 10;			//-- 한 페이지에 표시할 데이터 갯수	

		//③ 게시글 목록 조회: 가져올 게시글(RNUM)의 시작 번호, 끝 번호
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		//④ 페이징 처리 관련 주요 변수 선언
		int dataCount, totalPage;			//-- 전체 데이터 갯수, 전체 페이지 갯수
		String listUrl, pageIndexList;		//-- url, 페이징 처리

		//-- 전체 데이터 갯수, 전체 페이지 갯수
		dataCount = dao.count();
		totalPage = my.getPageCount(numPerPage, dataCount);

		//-- url, 페이징 처리
		listUrl = "accountlist.action";
		pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

		//○ 넘겨줄 자료 목록
		model.addAttribute("count", dataCount);					//-- 회원 수
		model.addAttribute("list", dao.list(start, end));		//-- 회원 목록
		model.addAttribute("pageIndexList", pageIndexList);		//-- 페이징 처리된 문자열

		return "WEB-INF/view/AccountList.jsp";
	}

	// 탈퇴 회원 리스트 조회
	@RequestMapping(value = "/withdrawalaccountlist.action", method = RequestMethod.GET)
	public String withdrawalaccountList(Model model, HttpSession session, String pageNum)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		IWithdrawalAccountListDAO dao = sqlSession.getMapper(IWithdrawalAccountListDAO.class);
		MyUtil my = new MyUtil();

		//① 현재 표시되어야 하는 페이지
		int currentPage = (pageNum != null) ? Integer.parseInt(pageNum) : 1;

		//② 전체 페이지를 기준으로 총 페이지 수 계산
		int numPerPage = 10;			//-- 한 페이지에 표시할 데이터 갯수	

		//③ 게시글 목록 조회: 가져올 게시글(RNUM)의 시작 번호, 끝 번호
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		//④ 페이징 처리 관련 주요 변수 선언
		int dataCount, totalPage;			//-- 전체 데이터 갯수, 전체 페이지 갯수
		String listUrl, pageIndexList;		//-- url, 페이징 처리

		//-- 전체 데이터 갯수, 전체 페이지 갯수
		dataCount = dao.withdrawalcount();
		totalPage = my.getPageCount(numPerPage, dataCount);

		//-- url, 페이징 처리
		listUrl = "withdrawalaccountlist.action";
		pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

		//○ 넘겨줄 자료 목록
		model.addAttribute("withdrawalcount", dataCount);							//-- 탈퇴회원 수
		model.addAttribute("withdrawallist", dao.withdrawallist(start, end));		//-- 탈퇴회원 목록
		model.addAttribute("pageIndexList", pageIndexList);							//-- 페이징 처리된 문자열

		return "WEB-INF/view/WithdrawalAccountList.jsp";
	}

}
