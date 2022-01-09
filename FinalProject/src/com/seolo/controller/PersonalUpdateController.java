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
import com.seolo.dto.ReportRunDTO;
import com.seolo.dto.ReportviewDTO;
import com.seolo.idao.IMainDAO;
import com.seolo.idao.IReportRunDAO;
import com.seolo.idao.IReportviewDAO;
import com.seolo.idao.IUpdateDAO;
import com.seolo.personal.PersonalDTO;

@Controller
public class PersonalUpdateController
{
	@Autowired
	private SqlSession sqlSession;	// mybatis 객체 의존성(자동) 주입
	

	// 마이페이지 내 정보 전체 조회
	
	// ★ 다영 수정
	// + 나의 신고리스트(최근 3개) 조회 가능하게 추가
	@RequestMapping(value = "/myinfo.action", method = RequestMethod.GET)
	public String myInfoForm(Model model, HttpSession session)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);
		
		if (session.getAttribute("userLogin")==null)	// 로그인 안하면 차단
		{
			return "redirect:main.action";
		}
		else
		{
			PersonalDTO userLogin = (PersonalDTO)session.getAttribute("userLogin");
			String pe_Id = userLogin.getPe_Id();
			
			PersonalDTO user = dao.searchId(pe_Id);
			model.addAttribute("user", user);
			
			// 나의 신고리스트 부분 추가
			String reportername = userLogin.getPe_Id();
			model.addAttribute("myinfoList", dao.myinfoList(reportername));
			
		}
		
		return "WEB-INF/view/MyInfo.jsp";
	}
	
	
	// ★ 소연 수정
	@RequestMapping(value = "/infoupdateconfirmform.action", method = RequestMethod.GET)
	public String updateConfirmform(Model model, HttpSession session, HttpServletRequest request)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);
		
		if (session.getAttribute("userLogin")==null)	// 로그인 안하면 차단
		{
			return "redirect:main.action";
		}
		else
		{
			String errMsg = null;
	        errMsg = request.getParameter("errMsg");
	        if (errMsg != null)   // 기존 비밀번호 다르게 입력한 사람들에게 에러메세지
	           model.addAttribute("errMsg", errMsg);
	        
		}
		
		return "WEB-INF/view/UpdateConfirm.jsp";
	}
	
	// ★
	@RequestMapping(value = "/infoupdateconfirm.action", method = RequestMethod.POST)
	public String updateConfirm(Model model, HttpSession session, HttpServletRequest request)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);
		
		if (session.getAttribute("userLogin")==null)	// 로그인 안하면 차단
		{
			return "redirect:main.action";
		}
		else
		{
			PersonalDTO user = new PersonalDTO();
			user.setPe_Id(request.getParameter("id"));
			user.setPw(request.getParameter("pwd"));
			
			int count = dao.confirmPwd(user);
			if (count!=1)
			{
				model.addAttribute("errMsg", "err");
				return "redirect:infoupdateconfirmform.action";
			}
			else
			{
				return "redirect:myinfoupdateform.action";
			}
	        
		}
		
	}
	
	@RequestMapping(value = "/myinfoupdateform.action", method = RequestMethod.GET)
	public String updateForm(Model model, HttpSession session)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);
		
		if (session.getAttribute("userLogin")==null)	// 로그인 안하면 차단
		{
			return "redirect:main.action";
		}

			//String updateId = id;
			// PersonalDTO user = dao.searchId(id);
			//model.addAttribute("updateId", updateId);
			String id = ((PersonalDTO)session.getAttribute("userLogin")).getPe_Id();
		
			model.addAttribute("user", dao.searchId(id));
			
			return "WEB-INF/view/MyInfoUpdate.jsp"; 
	}
	
	
	@RequestMapping(value = "/myinfoupdate.action",method = RequestMethod.POST)
	public String infoUpdate(Model model, HttpSession session,PersonalDTO dto)
	{
		IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);
		
		if (session.getAttribute("userLogin")==null)	// 로그인 안하면 차단
		{
			return "redirect:main.action";
		}
		
		dao.update(dto);

		return "WEB-INF/view/MyInfoUpdateFn.jsp";
		                     
	}
	
   // ★ 소연 수정
   @RequestMapping(value = "/pwdchangeform.action", method = RequestMethod.GET)
   public String pwdChangeForm(Model model, HttpSession session, HttpServletRequest request)
   {
      IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);
      
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         String errMsg = null;
         errMsg = request.getParameter("errMsg");
         if (errMsg != null)   // 기존 비밀번호 다르게 입력한 사람들에게 에러메세지
            model.addAttribute("errMsg", errMsg);
         
      }
      
      return "WEB-INF/view/PwdChange.jsp";
   }
   
   @RequestMapping(value = "/pwdchange.action", method = RequestMethod.POST)
   public String pwdChange(Model model, HttpSession session, String nowPwd, String newPwd)
   {
      IUpdateDAO dao = sqlSession.getMapper(IUpdateDAO.class);
      
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {   
         String id = ((PersonalDTO)session.getAttribute("userLogin")).getPe_Id();
         PersonalDTO user = new PersonalDTO();
         user.setPe_Id(id);
         user.setPw(nowPwd);
         
         int count = dao.confirmPwd(user);   // 입력한 기존 비밀번호가 일치하는지 확인(0이면 불일치, 1이면 일치)
         if (count==0)
         {
            model.addAttribute("errMsg", "err");
            return "redirect:pwdchangeform.action";   // 다르다면 변경 불가 → 폼으로 리다이렉트
         }
         
         user.setPw(newPwd);
         dao.updatePwd(user);   // 비밀번호 업데이트
      }
      
      return "WEB-INF/view/PwdChangeFn.jsp";
   }
	
   // ★ 다영 수정
   // 마이페이지 - <나의 신고리스트> 추가
   
   // 마이페이지 - 나의 신고리스트(전체) 조회로 이동 및 전체 조회
   // 나의 신고리스트 상세 페이지로 바로가기
   @RequestMapping(value = "/myInfoReportList.action", method = RequestMethod.GET)
	public String myInfoReportAllList(Model model, HttpSession session)
	{
	   // 값 못 받아옴^ㅠ^ 일단 주석처리... -> 받아오기 성공! (신고번호 11-15, 신고자 chunsik)
		IReportviewDAO dao = sqlSession.getMapper(IReportviewDAO.class);
	   
		if (session.getAttribute("userLogin")==null)
		{
			return "redirect:main.action";
		}
		else
		{
			PersonalDTO userLogin = (PersonalDTO)session.getAttribute("userLogin");
			String reportername = userLogin.getPe_Id();
			//rv.setReportername(reportername); -> 없어도 되는 부분임.
			
			model.addAttribute("myinfoAllList", dao.myinfoAllList(reportername));
		}
		
		return "WEB-INF/view/MyInfoReportList.jsp";
	}
   
   // 마이페이지 - 나의 신고리스트(전체)에서 신고 취소
	@RequestMapping(value = "/myInfoReportDelete.action", method = RequestMethod.GET)
	public String myInfoReportDelete(ReportRunDTO dto)
	{
		IReportRunDAO dao = sqlSession.getMapper(IReportRunDAO.class);
		
		dao.delete(dto);
		
		return "redirect:myInfoReportList.action";
	}
	
	// 마이페이지 - 나의 신고리스트(메인-최근 3개)에서 신고 취소
	@RequestMapping(value = "/myInfoReportMDelete.action", method = RequestMethod.GET)
	public String myInfoReportMDelete(ReportRunDTO dto)
	{
		IReportRunDAO dao = sqlSession.getMapper(IReportRunDAO.class);
		
		dao.delete(dto);
		
		return "redirect:myinfo.action";
	}
}
