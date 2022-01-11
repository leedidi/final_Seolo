/*==========================
   AdminController.java
   - 공지사항 컨트롤러
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
import com.seolo.dto.PageDTO;
import com.seolo.personal.PersonalDTO;

@Controller
public class AdminController
{
	/// mybatis 객체 의존성 (자동) 주입~!!!
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/mainadmin.action", method = RequestMethod.GET)
	public String mainadmin(Model model, HttpSession session)
	{
		
		AdminDTO adminSession = (AdminDTO)session.getAttribute("adminLogin");
		
		// 관리자가 아니면 접속 불가
		if (adminSession == null)
			return "redirect:main.action";
		
		return "WEB-INF/view/Main_admin.jsp";
	}
	
	// 공지사항 게시판 조회
	@RequestMapping(value = "/noticelist.action", method = RequestMethod.GET)
	public String noticeList(Model model, PageDTO dto, NoticeDTO ndto, HttpServletRequest request)
	{
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		
		// 페이징 처리
		MyUtil my = new MyUtil();
		// 사작여기서부터
		String pageNum = request.getParameter("pageNum");
		
		//System.out.println(pageNum);
		// 현재 표시되어야 하는 페이지 (* 주석처리함)
		
		int currentPage = 1;
		
		if(pageNum != null)
			currentPage = Integer.parseInt(pageNum);
		
		// 전체 페이지를 기준으로 총 페이지 수 계산
		int numPerPage = 10;	//-- 한 페이지에 표시할 데이터 갯수		
		
		int start = (currentPage-1) * numPerPage + 1;
		int end = currentPage * numPerPage;		

		// 페이징 처리
		String param = "";

		dto.setStart(start);
		dto.setEnd(end);		
		
		// 카테고리 버튼 클릭 시 해당 카테고리에 해당되는 게시물 뜨도록 추가 
		// 카테고리 번호 가져옴
		String notice_check = request.getParameter("notice_check");
		
		int dataCount, totalPage;
		String listUrl, pageIndexList;
		
		// 실제 리스트 가져오기
		if(notice_check != null)
		{
			// 카테고리 클릭시(notice_check 값 존재 시) 카테고리 번호 넘겨주고 카테고리 출력
			
			// 카운트를 xml에 메소드 하나 새로 만듬(카테고리별 총 수)
			// 해당 메소드를 실행
			// 페이지 인데스 실행!
			//-> 전체 페이지를 기준으로 총 페이지 수 계산
			
			// 전체 데이터 갯수 구하기	
			dataCount = dao.cateCount(notice_check);
			// 데이터베이스에서 가져올 시작과 끝 위치
			totalPage = my.getPageCount(numPerPage, dataCount);
			
			// url 추가
			listUrl = "noticelist.action?notice_check=" + notice_check;
			pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);
			
			ndto.setNotice_check(notice_check);
			model.addAttribute("list", dao.catelist(dto, start, end, notice_check));
			model.addAttribute("pageIndexList", pageIndexList);
			model.addAttribute("cateNamelist", dao.cateNamelist());
			
		}
		else
		{	// 카테고리 미 클릭 시(notice_check 값 존재하지 않을 시) 전체 리스트 출력
			
			// 전체 데이터 갯수 구하기		
			dataCount = dao.count();
			// 데이터베이스에서 가져올 시작과 끝 위치
			totalPage = my.getPageCount(numPerPage, dataCount);
			
			// url 추가
			listUrl = "noticelist.action" + param;
			pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);
			
			model.addAttribute("list", dao.list(dto));
			model.addAttribute("pageIndexList", pageIndexList);
			model.addAttribute("cateNamelist", dao.cateNamelist());
		}
		
		return "WEB-INF/view/NoticeList.jsp";
	}

	// 공지사항 게시물 작성 폼으로 이동
	@RequestMapping(value = "/writenotice.action", method = RequestMethod.GET)
	public String writeNotice(Model model)
	{
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		
		return "WEB-INF/view/WriteNotice.jsp";
	}

	// 공지사항 게시물 작성
	// (+) 로그인 세션 작성 후, 관리자 아이디 받아와서 INSERT에 넣어주는 기능 필요
	// -> 추가 완료.
	@RequestMapping(value = "/noticeinsert.action", method = RequestMethod.POST)
	public String noticeInsert(NoticeDTO n, HttpSession session)
	{
	
		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO)session.getAttribute("adminLogin");
		String ad_id = admin.getAd_Id();
		n.setAd_id(ad_id);
		
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		
		dao.add(n);
		
		return "redirect:noticelist.action";
	}
	
	// 공지사항 게시물 조회
	@RequestMapping(value = "/noticeview.action", method = RequestMethod.GET)
	public String noticeView(@RequestParam("no_no") int no_no, Model model, HttpSession session)
	{
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		
		model.addAttribute("view", dao.view(no_no));
		
		return "WEB-INF/view/NoticeSee.jsp";
	}
	
	// 공지사항 게시물 수정 폼으로 이동
	@RequestMapping(value = "/noticeupdateform.action", method = RequestMethod.GET)
	public String noticeUpdateform(@RequestParam("no_no") int no_no, Model model)
	{
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);

		model.addAttribute("view", dao.view(no_no));
		
		return "WEB-INF/view/UpdateNotice.jsp";
	}
	
	// 공지사항 게시물 수정
	// 다수의 오류 과정 생겼으나 해결! no_no 번호를 jsp 부분에서 히든으로 넣어서 받아옴
	@RequestMapping(value = "/noticeupdate.action", method = RequestMethod.POST)
	public String noticeUpdate(NoticeDTO n)
	{
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		
		dao.modify(n);
		
		return "redirect:noticeview.action?no_no=" + n.getNo_no();
	}

	// 공지사항 게시물 삭제
	@RequestMapping(value = "/noticedelete.action", method = RequestMethod.GET)
	public String noticeDelete(NoticeDTO n)
	{
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		
		dao.remove(n);
		
		return "redirect:noticelist.action";
	}
	
}
