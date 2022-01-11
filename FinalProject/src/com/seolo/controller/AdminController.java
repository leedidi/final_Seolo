/* ==========================
 * AdminController.java
 * - 공지사항 컨트롤러
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

import com.seolo.admin.INoticeDAO;
import com.seolo.admin.NoticeDTO;
import com.seolo.dto.AdminDTO;
import com.seolo.dto.PageDTO;

@Controller
public class AdminController
{
	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/mainadmin.action", method = RequestMethod.GET)
	public String mainadmin(Model model, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		return "WEB-INF/view/Main_admin.jsp";
	}

	// 공지사항 게시판 조회
	@RequestMapping(value = "/noticelist.action", method = RequestMethod.GET)
	public String noticeList(Model model, PageDTO dto, String pageNum, String notice_check)
	{
		//○ 로그인 세션 확인 필요 X

		//○ 게시판 조회 처리
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		MyUtil my = new MyUtil();

		//① 현재 표시되어야 하는 페이지
		int currentPage = (pageNum != null) ? Integer.parseInt(pageNum) : 1;

		//② 한 페이지에 표시할 데이터 갯수		
		int numPerPage = 10;

		//③ 게시글 목록 조회: 가져올 게시글(RNUM)의 시작 번호, 끝 번호
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		dto.setStart(start);
		dto.setEnd(end);

		//④ 페이징 처리 관련 주요 변수 선언
		int dataCount, totalPage;			//-- 전체 데이터 갯수, 전체 페이지 갯수
		String listUrl, pageIndexList;		//-- url, 페이징 처리

		//⑤ 게시글 목록, 페이징 처리
		if(notice_check != null)
		{
			//○ 카테고리 선택 시(notice_check(카테고리 번호) 값 존재)
			//　 → url 에 카테고리 정보 포함, 카테고리와 관련된 리스트 출력 

			//-- 전체 데이터 갯수, 전체 페이지 갯수
			dataCount = dao.cateCount(notice_check);
			totalPage = my.getPageCount(numPerPage, dataCount);

			//-- url, 페이징 처리
			listUrl = "noticelist.action?notice_check=" + notice_check;
			pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

			//-- 카테고리 선택 시 게시글 목록
			dto.setNotice_check(notice_check);				// 카테고리 선택 정보 추가
			model.addAttribute("list", dao.catelist(dto));

		}
		else
		{
			//○ 카테고리 미 선택 시(notice_check(카테고리 번호) 값 존재 Ｘ)
			//　 → 전체 리스트 출력

			//-- 전체 데이터 갯수, 전체 페이지 갯수
			dataCount = dao.count();
			totalPage = my.getPageCount(numPerPage, dataCount);

			//-- url, 페이징 처리
			listUrl = "noticelist.action";
			pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

			//-- 게시글 목록
			model.addAttribute("list", dao.list(dto));

		}

		model.addAttribute("pageIndexList", pageIndexList);		//-- 페이징 처리된 문자열
		model.addAttribute("cateNamelist", dao.cateNamelist());	//-- 카테고리 목록

		return "WEB-INF/view/NoticeList.jsp";
	}

	// 공지사항 게시물 작성 폼으로 이동
	@RequestMapping(value = "/writenotice.action", method = RequestMethod.GET)
	public String writeNotice(Model model, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		return "WEB-INF/view/WriteNotice.jsp";
	}

	// 공지사항 게시물 작성
	@RequestMapping(value = "/noticeinsert.action", method = RequestMethod.POST)
	public String noticeInsert(NoticeDTO n, HttpSession session)
	{
		// AC_NO, 관리자 아이디 받아와서 테이블에 insert 해줘야 함!
		// 체크 고유번호는 시퀀스를 통해 알아서 입력

		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO) session.getAttribute("adminLogin");
		String ad_id = admin.getAd_Id();
		n.setAd_id(ad_id);

		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		dao.add(n);

		return "redirect:noticelist.action";
	}

	// 공지사항 게시물 조회
	@RequestMapping(value = "/noticeview.action", method = RequestMethod.GET)
	public String noticeView(@RequestParam("no_no") int no_no, Model model)
	{
		//○ 로그인 세션 확인 필요 X

		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		model.addAttribute("view", dao.view(no_no));

		return "WEB-INF/view/NoticeSee.jsp";
	}

	// 공지사항 게시물 수정 폼으로 이동
	@RequestMapping(value = "/noticeupdateform.action", method = RequestMethod.GET)
	public String noticeUpdateform(@RequestParam("no_no") int no_no, Model model, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		model.addAttribute("view", dao.view(no_no));

		return "WEB-INF/view/UpdateNotice.jsp";
	}

	// 공지사항 게시물 수정
	// → no_no 번호를 jsp 부분에서 히든으로 넣어서 받아옴
	@RequestMapping(value = "/noticeupdate.action", method = RequestMethod.POST)
	public String noticeUpdate(NoticeDTO n, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		dao.modify(n);

		return "redirect:noticeview.action?no_no=" + n.getNo_no();
	}

	// 공지사항 게시물 삭제
	@RequestMapping(value = "/noticedelete.action", method = RequestMethod.GET)
	public String noticeDelete(NoticeDTO n, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		dao.remove(n);

		return "redirect:noticelist.action";
	}

}
