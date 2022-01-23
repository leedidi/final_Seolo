package com.seolo.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.seolo.dto.ReportRunDTO;
import com.seolo.idao.IReportRunDAO;
import com.seolo.idao.IReportviewDAO;
import com.seolo.idao.IUpdateDAO;
import com.seolo.personal.PersonalDTO;

@Controller
public class PersonalUpdateController
{
	@Autowired
	private SqlSession sqlSession;

	// 마이페이지 내 정보 전체 조회

	// ★ 다영 수정
	// + 나의 신고리스트(최근 3개) 조회 가능하게 추가
	@RequestMapping(value = "/myinfo.action", method = RequestMethod.GET)
	public String myInfoForm(Model model, HttpSession session)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);

		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		//○ 주요 기능 실행
		PersonalDTO userLogin = (PersonalDTO) session.getAttribute("userLogin");
		String pe_Id = userLogin.getPe_Id();

		PersonalDTO user = dao.searchId(pe_Id);
		model.addAttribute("user", user);

		// 나의 신고리스트 부분 추가
		String reportername = userLogin.getPe_Id();
		model.addAttribute("myinfoList", dao.myinfoList(reportername));

		return "WEB-INF/view/MyInfo.jsp";
	}

	// ★ 소연 수정
	@RequestMapping(value = "/infoupdateconfirmform.action", method = RequestMethod.GET)
	public String updateConfirmform(Model model, HttpSession session, String errMsg)
	{
		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		//○ 주요 기능 실행
		if(errMsg != null)   // 기존 비밀번호 다르게 입력한 사람들에게 에러메세지
			model.addAttribute("errMsg", errMsg);

		return "WEB-INF/view/UpdateConfirm.jsp";
	}

	@RequestMapping(value = "/infoupdateconfirm.action", method = RequestMethod.POST)
	public String updateConfirm(Model model, HttpSession session, String id, String pwd)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);

		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		//○ 주요 기능 실행
		PersonalDTO user = new PersonalDTO();
		user.setPe_Id(id);
		user.setPw(pwd);

		int count = dao.confirmPwd(user);
		if(count != 1)
		{
			model.addAttribute("errMsg", "err");
			return "redirect:infoupdateconfirmform.action";
		}
		else
			return "redirect:myinfoupdateform.action";

	}

	@RequestMapping(value = "/myinfoupdateform.action", method = RequestMethod.GET)
	public String updateForm(Model model, HttpSession session)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);

		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		String id = ((PersonalDTO) session.getAttribute("userLogin")).getPe_Id();
		model.addAttribute("user", dao.searchId(id));

		return "WEB-INF/view/MyInfoUpdate.jsp";
	}

	@RequestMapping(value = "/myinfoupdate.action", method = RequestMethod.POST)
	public String infoUpdate(Model model, HttpSession session, PersonalDTO dto)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);

		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		dao.update(dto);

		return "WEB-INF/view/MyInfoUpdateFn.jsp";

	}

	// ★ 소연 수정
	@RequestMapping(value = "/pwdchangeform.action", method = RequestMethod.GET)
	public String pwdChangeForm(Model model, HttpSession session, String errMsg)
	{
		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		if(errMsg != null)   // 기존 비밀번호 다르게 입력한 사람들에게 에러메세지
			model.addAttribute("errMsg", errMsg);

		return "WEB-INF/view/PwdChange.jsp";
	}

	@RequestMapping(value = "/pwdchange.action", method = RequestMethod.POST)
	public String pwdChange(Model model, HttpSession session, String nowPwd, String newPwd)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);

		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		//○ 주요 기능 실행
		String id = ((PersonalDTO) session.getAttribute("userLogin")).getPe_Id();
		PersonalDTO user = new PersonalDTO();
		user.setPe_Id(id);
		user.setPw(nowPwd);

		int count = dao.confirmPwd(user);   // 입력한 기존 비밀번호가 일치하는지 확인(0: 불일치, 1: 일치)
		if(count == 0)
		{
			model.addAttribute("errMsg", "err");
			return "redirect:pwdchangeform.action";   // 다르다면 변경 불가 → 폼으로 리다이렉트
		}

		user.setPw(newPwd);
		dao.updatePwd(user);   // 비밀번호 업데이트

		return "WEB-INF/view/PwdChangeFn.jsp";
	}

	// 마이페이지 - 나의 신고리스트(전체) 조회로 이동 및 전체 조회
	// 나의 신고리스트 상세 페이지로 바로가기
	@RequestMapping(value = "/myInfoReportList.action", method = RequestMethod.GET)
	public String myInfoReportAllList(Model model, HttpSession session, String pageNum)
	{
		IReportviewDAO dao = sqlSession.getMapper(IReportviewDAO.class);

		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		//○ 주요 기능 실행
		PersonalDTO userLogin = (PersonalDTO) session.getAttribute("userLogin");
		String reportername = userLogin.getPe_Id();
		
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

		//-- 전체 데이터 갯수, 전체 페이지 갯수
		dataCount = dao.myCount(reportername);
		totalPage = my.getPageCount(numPerPage, dataCount);

		//-- url, 페이징 처리
		listUrl = "myInfoReportList.action";
		pageIndexList = my.pageIndexList(currentPage, totalPage, listUrl);

		//-- 반환값 처리
		model.addAttribute("myinfoAllList", dao.myinfoAllList(start, end, reportername));	//-- 게시글 목록
		model.addAttribute("pageIndexList", pageIndexList);		//-- 페이징 처리된 문자열

		return "WEB-INF/view/MyInfoReportList.jsp";
	}

	// 마이페이지 : 신고 취소
	@RequestMapping(value = "/myInfoReportDelete.action", method = RequestMethod.GET)
	public String myInfoReportDelete(ReportRunDTO dto, HttpSession session)
	{
		IReportRunDAO dao = sqlSession.getMapper(IReportRunDAO.class);

		//○ 세션 확인: 로그인 정보가 없을 시 → 로그인 페이지로 
		if(session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		//○ 주요 기능 실행
		dao.delete(dto);

		return "redirect:myInfoReportList.action";
	}
}
