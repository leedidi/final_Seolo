/* ==========================
 * FaqController.java
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
import com.seolo.dto.FaqRunDTO;
import com.seolo.dto.FaqviewDTO;
import com.seolo.idao.IFaqRunDAO;
import com.seolo.idao.IFaqviewDAO;

@Controller
public class FaqController
{
	@Autowired
	private SqlSession sqlSession;

	// 자주 묻는 게시판 처리 게시판 조회
	@RequestMapping(value = "/faqlist.action", method = RequestMethod.GET)
	public String faqList(Model model, FaqviewDTO dto, String pageNum, String faq_check)
	{
		//○ 로그인 세션 확인 필요 X

		//○ 게시판 조회
		IFaqviewDAO dao = sqlSession.getMapper(IFaqviewDAO.class);
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

		//⑤ 게시글 목록, 페이징 처리
		if(faq_check != null)
		{
			//○ 카테고리 선택 시(faq_check(카테고리 번호) 값 존재)
			//　 → url 에 카테고리 정보 포함, 카테고리와 관련된 리스트 출력 

			//-- 전체 데이터 갯수, 전체 페이지 갯수
			dataCount = dao.cateCount(faq_check);					//-- faq_check 정보 넘겨줘야 함...
			totalPage = my.getPageCount(numPerPage, dataCount);

			//-- url, 페이징 처리
			listUrl = "faqlist.action?faq_check=" + faq_check;
			pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

			//-- 카테고리 선택 시 게시글 목록
			model.addAttribute("list", dao.cateList(start, end, faq_check));
		}
		else
		{
			//○ 카테고리 미 선택 시(faq_check(카테고리 번호) 값 존재 Ｘ)
			//　 → 전체 리스트 출력

			//-- 전체 데이터 갯수, 전체 페이지 갯수
			dataCount = dao.count();
			totalPage = my.getPageCount(numPerPage, dataCount);

			//-- url, 페이징 처리
			listUrl = "faqlist.action";
			pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

			//-- 게시글 목록
			model.addAttribute("list", dao.list(start, end));
		}
		
		model.addAttribute("pageIndexList", pageIndexList);		//-- 페이징 처리된 문자열
		model.addAttribute("cateNameList", dao.cateNameList());	//-- 카테고리 목록

		return "WEB-INF/view/FaqListNode.jsp";
	}

	// 자주 묻는 게시판 생성 페이지로 보내기
	@RequestMapping(value = "/faqinsertform.action", method = RequestMethod.GET)
	public String FaqRunInsertform(Model model, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		return "WEB-INF/view/WriteFAQ.jsp";
	}

	// 자주 묻는 게시판 입력 기능 수행
	@RequestMapping(value = "/faqinsert.action", method = RequestMethod.POST)
	public String FaqInsert(FaqRunDTO n, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		// 관리자 아이디 세션에서 받아와서 추가
		AdminDTO admin = (AdminDTO) session.getAttribute("adminLogin");
		String ad_id = admin.getAd_Id();
		n.setAd_id(ad_id);

		IFaqRunDAO dao = sqlSession.getMapper(IFaqRunDAO.class);
		dao.add(n);

		return "redirect:faqlist.action";
	}

	// 자주 묻는 질문 게시판 수정 페이지로 보내기
	@RequestMapping(value = "/faqupdateform.action", method = RequestMethod.GET)
	public String faqUpdateform(@RequestParam("faq_no") int faq_no, Model model, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		IFaqviewDAO dao = sqlSession.getMapper(IFaqviewDAO.class);
		model.addAttribute("view", dao.view(faq_no));

		return "WEB-INF/view/UpdateFAQ.jsp";
	}

	// 자주 묻는 질문 게시물 수정하기
	@RequestMapping(value = "/faqupdate.action", method = RequestMethod.POST)
	public String faqUpdate(FaqRunDTO n, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		IFaqRunDAO dao = sqlSession.getMapper(IFaqRunDAO.class);
		dao.modify(n);

		return "redirect:faqlist.action";
	}

	// 자주 묻는 질문 게시물 삭제하기
	@RequestMapping(value = "/faqdelete.action", method = RequestMethod.GET)
	public String faqDelete(FaqRunDTO n, HttpSession session)
	{
		//○ 세션 확인: 관리자 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("adminLogin") == null)
			return "redirect:adminloginform.action";

		IFaqRunDAO dao = sqlSession.getMapper(IFaqRunDAO.class);
		dao.remove(n);

		return "redirect:faqlist.action";
	}

	/* // 자주 묻는 게시판 처리 게시판 - 카테고리별 조회
	 * 
	 * @RequestMapping(value = "/faqCatelist.action", method = RequestMethod.GET)
	 * public String reportCateList(Model model)
	 * {
	 * IFaqviewDAO dao = sqlSession.getMapper(IFaqviewDAO.class);
	 * 
	 * model.addAttribute("list", dao.list());
	 * 
	 * return "WEB-INF/view/FaqListNode.jsp";
	 * } */

}
