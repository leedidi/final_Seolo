/*=======================================
   ChecklistController.java : 진하
=========================================*/

package com.seolo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.seolo.dto.StickerDTO;
import com.seolo.dto.ChecklistDTO;
import com.seolo.dto.PlusDTO;
import com.seolo.idao.ICheckStickerDAO;
import com.seolo.idao.IChecklistDAO;
import com.seolo.idao.IPlusDAO;
import com.seolo.personal.PersonalDTO;


@Controller
public class ChecklistController
{
   @Autowired
   private SqlSession sqlSession;

   // 1번 체크리스트 입력 폼으로 보내준다
   @RequestMapping(value = "/checkfirstinsertform.action", method = RequestMethod.GET)
   public String firstInsertForm(Model model, HttpSession session)
   {
      IChecklistDAO dao = sqlSession.getMapper(IChecklistDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         model.addAttribute("scores", dao.scoreList());
         // model.addAttribute("residences", dao.residenceList());
      }
      
      // return "ChecklistWrite_first.jsp";
      return "WEB-INF/view/ChecklistWrite_first.jsp";
   }

   // 1번 체크리스트 입력해준다
   // submit 한 결과니까 post
   @RequestMapping(value = "/checkfirstinsert.action", method = RequestMethod.POST)
   public String firstInsert(Model model, HttpSession session, ChecklistDTO dto, HttpServletRequest request)
   {
      IChecklistDAO dao = sqlSession.getMapper(IChecklistDAO.class);

      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
           
         int acNo = Integer.parseInt(user.getAc_No());
         
         dto.setAcNo(acNo);
         
         String dongNo = request.getParameter("bcode");
         String roadAddr = request.getParameter("roadAddr1");
         
         // System.out.println(roadAddr);
         // System.out.println(dongNo);
         
         dto.setDongNo(dongNo);
         dto.setRoadAddr(roadAddr);
         
         dao.add(dto);         // 필수입력사항 인서트

         // 현 체크리스트번호 얻어내기
         int checkNo = dao.searchCheckNo(acNo);   
         dto.setCheckNo(checkNo);


         if (request.getParameter("mWolse") != null)
         {
            // System.out.println("월세 실행");
            // System.out.println(request.getParameter("mWolse"));
            dao.addWolse(dto);
         }
         if (request.getParameter("mJeonse") != null)
            dao.addJeonse(dto);
         if (request.getParameter("mMaemae") != null)
            dao.addMaemae(dto);

         if (Integer.parseInt(request.getParameter("pet_scoreNo")) != -1)
            dao.addPet(dto);
         if (request.getParameter("pet_co") != null)
            dao.addPetco(dto);

         if (Integer.parseInt(request.getParameter("security_scoreNo")) != -1)
            dao.addSecurity(dto);
         if (request.getParameter("security_co") != null)
            dao.addSecurityco(dto);

         if (Integer.parseInt(request.getParameter("transport_scoreNo")) != -1)
            dao.addTransport(dto);
         if (request.getParameter("transport_co") != null)
            dao.addTransportco(dto);

         if (Integer.parseInt(request.getParameter("honjap_scoreNo")) != -1)
            dao.addHonjap(dto);
         if (request.getParameter("honjap_co") != null)
            dao.addHonjapco(dto);

         if (request.getParameter("good") != null)
            dao.addContentG(dto);
         if (request.getParameter("bad") != null)
            dao.addContentB(dto);
         if (request.getParameter("etc") != null)
            dao.addContentE(dto);

         if (request.getParameter("secret_co") != null)
            dao.addSecret(dto);

         if (request.getParameter("mart") != null)       
            dao.addConvenience(dto);
         if (request.getParameter("conv_co") != null)
            dao.addConvenienceco(dto);
         
         
         // 해당 체크리스트 번호 추가항목입력 페이지에 넘겨주기
         model.addAttribute("checkNo", checkNo);
         
      }
      
      // return "ChecklistWrite_second.jsp";
      return "WEB-INF/view/ChecklistWrite_second.jsp";   
      // return "checksecondinsert.action";
   }
   
   
   

   
   // 1번 체크리스트에서 주소 ajax 처리한다
   @RequestMapping(value = "/ajax.action", method = RequestMethod.POST)
   public String addressAjax(Model model, HttpSession session, HttpServletRequest request)
   {
      IChecklistDAO dao = sqlSession.getMapper(IChecklistDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         String bcode = request.getParameter("bcode");

         // System.out.println(bcode);

         int check = dao.checkdong(bcode);

         String result = "";

         if (check == 0)
         {
            result = "해당 지역은 서울이 아닙니다. 다시 검색해주세요";
         } else
         {
            result = dao.searchDongName(bcode);
         }

         model.addAttribute("result", result);
      }

      // return "Adress_ok.jsp";
      return "WEB-INF/view/Adress_ok.jsp";   
   }

   
   //////// 두번째 입력폼 /////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   // 추가!! 2번 입력폼을 불러와준다 - 리다이렉트하기
   @RequestMapping(value="/checksecondinsert.action", method=RequestMethod.GET)
   public String secondInsertForm(Model model, HttpSession session, PlusDTO dto, HttpServletRequest request)
   {
	   IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);
	   
	   if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
    	  // 여기서 해줘야할 것 : 가져와라 이 체크리스트에 해당하는 목록들을 
    	  PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
          int acNo = Integer.parseInt(user.getAc_No());
          
          int checkNo = dao.searchCheckNo(acNo);
          
          dto.setCheckNo(String.valueOf(checkNo));
          
          
          ArrayList<PlusDTO> timeList = new ArrayList<PlusDTO>();
          ArrayList<PlusDTO> scoreList = new ArrayList<PlusDTO>();
          
          
          // 만약 시간 추가항목이 null이 아니라면 리스트를 넘겨주겠다.
          if(dao.timeList(checkNo) != null)
        	  timeList = dao.timeList(checkNo);
          
          
          // 만약 점수 추가항목이 null이 아니라면 리스트를 넘겨주겠다
          if(dao.scoreList(checkNo) != null)
             scoreList = dao.scoreList(checkNo);
          
          
          model.addAttribute("timeList", timeList);
          model.addAttribute("scoreList", scoreList);
    	  
      }
	   
	   return "WEB-INF/view/ChecklistWrite_second.jsp";
   }

   
   
   
   
   // 시간 추가항목 입력폼으로 보내기
   @RequestMapping(value="/secondtimeinsertform.action", method=RequestMethod.GET)
   public String plusTimeInsertForm(Model model, HttpSession session, ChecklistDTO dto, HttpServletRequest request)
   {
      IChecklistDAO dao = sqlSession.getMapper(IChecklistDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());
         
         dto.setAcNo(acNo);

         // 1번 입력페이지에서 넘겨준 체크리스트 고유번호 받기
         // String checkNo = request.getParameter("checkNo");
         
         int checkNo = dao.searchCheckNo(acNo);
         
         // System.out.println(checkNo);
         
         model.addAttribute("checkNo", checkNo);
      }
      //int acNo = 28;       // 나중에 세션에서 받아와야함
      
      // return "SecondTimeInsertForm.jsp";
      return "WEB-INF/view/SecondTimeInsertForm.jsp";
   }

   
   // 점수 추가항목 입력폼으로 보내기
   @RequestMapping(value="/secondscoreinsertform.action", method=RequestMethod.GET)
   public String plusScoreInsertForm(Model model,HttpSession session, ChecklistDTO dto, HttpServletRequest request)
   {
      IChecklistDAO dao = sqlSession.getMapper(IChecklistDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());
         
         dto.setAcNo(acNo);

         // 1번 입력페이지에서 넘겨준 체크리스트 고유번호 받기
         // String checkNo = request.getParameter("checkNo");
         
         int checkNo = dao.searchCheckNo(acNo);
         
         model.addAttribute("scores", dao.scoreList());
         model.addAttribute("checkNo", checkNo);
         
      }
      
      // int acNo = 23;       // 나중에 세션에서 받아와야함
      
      // return "SecondScoreInsertForm.jsp";
      return "WEB-INF/view/SecondScoreInsertForm.jsp";
      
   }


   // 시간 추가항목 입력
   @RequestMapping(value="/secondtimeinsert.action", method=RequestMethod.GET)
   public String plusTimeInsert(Model model, HttpSession session, PlusDTO dto, HttpServletRequest request)
   {
      IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());

         // System.out.println("no: " + request.getParameter("checkNo"));
         // System.out.println("title : " + request.getParameter("timeTitle"));
         // System.out.println("time : " + request.getParameter("time"));
         // System.out.println("comment : " + request.getParameter("timeComments"));
         
         // String checkNo = request.getParameter("checkNo");
         
         // int acNo = 23;   // 나중에 세션에서 받아와야함
         
         int checkNo = dao.searchCheckNo(acNo);
         // System.out.println("체크고유번호 : "+ checkNo);
         
         dto.setCheckNo(String.valueOf(checkNo));
         
         // 코멘트가 있을 때 - 전체 입력
         if(request.getParameter("timeComments") != null)
            dao.addTimeco(dto);
         else               
            dao.addTime(dto);
         
         ArrayList<PlusDTO> timeList = new ArrayList<PlusDTO>();
         timeList = dao.timeList(checkNo);
         
         ArrayList<PlusDTO> scoreList = new ArrayList<PlusDTO>();
         
         // 만약 점수 추가항목이 null이 아니라면 리스트를 넘겨주겠다
         if(dao.scoreList(checkNo) != null)
            scoreList = dao.scoreList(checkNo);
         
         
         model.addAttribute("timeList", timeList);
         model.addAttribute("scoreList", scoreList);
      }
      
      // return "ChecklistWrite_second.jsp";
      // return "WEB-INF/view/ChecklistWrite_second.jsp";
   
      return "redirect:checksecondinsert.action";
   }

   
   // 점수 추가항목 입력
   @RequestMapping(value="/secondscoreinsert.action", method=RequestMethod.GET)
   public String plusScoreInsert(Model model, HttpSession session, PlusDTO dto, HttpServletRequest request)
   {
      IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());
      
         // System.out.println("title : " + request.getParameter("scoreTitle"));
         // System.out.println("score : " + request.getParameter("scoreNo"));
         // System.out.println("comment : " + request.getParameter("scoreComments"));
   
         // int acNo = 23;      // 임시 나중에 세션에서 받아와야함
         int checkNo = dao.searchCheckNo(acNo);
         // System.out.println("체크고유번호 : "+ checkNo);
         
         dto.setCheckNo(String.valueOf(checkNo));
         
         // 코멘트가 있을 때 - 전체 입력
         if(request.getParameter("scoreComments") != null)
            dao.addScoreco(dto);
         else               
            dao.addScore(dto);
         
         ArrayList<PlusDTO> timeList = new ArrayList<PlusDTO>();
         timeList = dao.timeList(checkNo);
         ArrayList<PlusDTO> scoreList = new ArrayList<PlusDTO>();
         
         if(dao.scoreList(checkNo) != null)
            scoreList = dao.scoreList(checkNo);
         
         model.addAttribute("timeList", timeList);
         model.addAttribute("scoreList", scoreList);
      }
      
      // return "ChecklistWrite_second.jsp";
      // return "WEB-INF/view/ChecklistWrite_second.jsp";
   
      return "redirect:checksecondinsert.action";
   }
   


   
   // 시간 추가항목 삭제
   @RequestMapping(value="/secondtimedelete.action", method=RequestMethod.GET)
   public String plusTimeDelete(Model model, HttpSession session, HttpServletRequest request)
   {
      IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());
      
         String plusTimeNo = request.getParameter("plusTimeNo");
         
         dao.removeTime(Integer.parseInt(plusTimeNo));
         
         // int acNo = 23;      // 임시 나중에 세션에서 받아와야함
         int checkNo = dao.searchCheckNo(acNo);
         
         // ArrayList<PlusDTO> timeList = dao.timeList(checkNo);
         // ArrayList<PlusDTO> scoreList = dao.scoreList(checkNo);
         
         model.addAttribute("timeList", dao.timeList(checkNo));
         model.addAttribute("scoreList", dao.scoreList(checkNo));
      }
      
      // return "ChecklistWrite_second.jsp";
      // return "WEB-INF/view/ChecklistWrite_second.jsp";
      
      return "redirect:checksecondinsert.action";
      
   }

   
   // 점수 추가항목 삭제
   @RequestMapping(value="/secondscoredelete.action", method=RequestMethod.GET)
   public String plusScoreDelete(Model model, HttpSession session, HttpServletRequest request)
   {
      IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());   
         
         String plusScoreNo = request.getParameter("plusScoreNo");
         
         dao.removeScore(Integer.parseInt(plusScoreNo));
         
         // int acNo = 23;      // 임시. 나중에 세션에서 받아와야함
         int checkNo = dao.searchCheckNo(acNo);
         
         // ArrayList<PlusDTO> timeList = dao.timeList(checkNo);
         // ArrayList<PlusDTO> scoreList = dao.scoreList(checkNo);
         
         model.addAttribute("timeList", dao.timeList(checkNo));
         model.addAttribute("scoreList", dao.scoreList(checkNo));
      }
      
      // return "ChecklistWrite_second.jsp";
      // return "WEB-INF/view/ChecklistWrite_second.jsp";
      
      return "redirect:checksecondinsert.action";
   }
   
   
   
   // 시간 추가항목 수정폼으로 보내기
   @RequestMapping(value="/secondtimeupdateform.action", method=RequestMethod.GET)
   public String plusTimeUpdateForm(Model model, HttpSession session, HttpServletRequest request)
   {
      IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         int plusTimeNo = Integer.parseInt(request.getParameter("plusTimeNo"));
   
         model.addAttribute("plusTimeNo", plusTimeNo);
         model.addAttribute("time", dao.plusTime(plusTimeNo));
      }
      
      // return "SecondTimeUpdateForm.jsp";
      return "WEB-INF/view/SecondTimeUpdateForm.jsp";
   
   }
   
   
   // 점수 추가항목 수정폼으로 보내기
   @RequestMapping(value="/secondscoreupdateform.action", method=RequestMethod.GET)
   public String plusScoreUpdateForm(Model model, HttpSession session, HttpServletRequest request)
   {
      IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {      
         int plusScoreNo = Integer.parseInt(request.getParameter("plusScoreNo"));
         
         System.out.println("plusScoreNo : " + plusScoreNo);
   
         model.addAttribute("plusScoreNo", plusScoreNo);
         model.addAttribute("score", dao.plusScore(plusScoreNo));
         model.addAttribute("scores", dao.scores());
      }
      
      // return "SecondScoreUpdateForm.jsp";
      return "WEB-INF/view/SecondScoreUpdateForm.jsp";

   }
   
   
   // 시간 추가항목 수정하기 
   @RequestMapping(value="/secondtimeupdate.action", method=RequestMethod.GET)
   public String plusTimeUpdate(@RequestParam("plusTimeNo") String plusTimeNo , Model model, HttpSession session, PlusDTO dto, HttpServletRequest request)
   {
      IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);

      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {   
        // System.out.println("plusTimeNo1 : " + request.getParameter("plusTimeNo"));
        // System.out.println("title : " + request.getParameter("timeTitle"));
        // System.out.println("time : " + request.getParameter("time"));
        // System.out.println("comment : " + request.getParameter("timeComments"));
    	  
    	 
    	 // 여기 수정중.. 혹시나 plusTimeNo문제인가 했는데.. 아니네ㅋ
    	 dto.setPlusTimeNo(Integer.parseInt(plusTimeNo));
    	 
    	 // System.out.println("plusTimeNo2 : " + request.getParameter("plusTimeNo"));
         
         if(request.getParameter("timeComments") != null)
            dao.modifyTimeco(dto);
         else
            dao.modifyTime(dto);
      }
      
      // 어떤 컨트롤러가 문제인지 보려고(수정컨트롤러 or 리다이렉트컨트롤러) 아예 다른 컨트롤러 불러봤는데 안불러져
      // 귀신들린게 분명함.... HELPPP
      // return "redirect:thirdinsertform.action"; 여기로 가지지 않고 이 밑에걸로 계속 가져....
      
      return "redirect:checksecondinsert.action";
   
   }
   
   
   // 점수 추가항목 수정하기 
   @RequestMapping(value="/secondscoreupdate.action", method=RequestMethod.GET)
   public String plusScoreUpdate(Model model, HttpSession session, PlusDTO dto, HttpServletRequest request)
   {
      IPlusDAO dao = sqlSession.getMapper(IPlusDAO.class);

      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {   
         if(request.getParameter("scoreComments") != null)
            dao.modifyScoreco(dto);
         else
            dao.modifyScore(dto);
      }
         
      // return "ChecklistWrite_second.jsp";
      // return "WEB-INF/view/ChecklistWrite_second.jsp";
      
      return "redirect:checksecondinsert.action";
   }
   

   //////// 세번째 입력폼 ////////////////////////////////////////////////////////////////////////////////////////////////
   
   // 스티커 입력폼으로 보내기
   @RequestMapping(value="/thirdinsertform.action", method = RequestMethod.GET)
   public String thirdInsertform(Model model, HttpSession session)
   {
      ICheckStickerDAO dao = sqlSession.getMapper(ICheckStickerDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());         
         // int acNo = 1;      // 임시. 나중에 세션에서 받아와야함
         
         ArrayList<StickerDTO> list = dao.stickerList(acNo);
         
         // System.out.println("스티커 번호 : " + list.get(0).getContent());
         // System.out.println("스티커 내용 : " + list.content);
         
         
         model.addAttribute("list",list);
         
      }
      
      // return "ChecklistWrite_third.jsp";
      return "WEB-INF/view/ChecklistWrite_third.jsp";
      
   }
   
   
   // 기존의 스티커를 선택하면 선택한 스티커에 추가하기
   @RequestMapping(value="/selectsticker.action", method = RequestMethod.GET)
   public String selectSticker(Model model, HttpSession session, StickerDTO dto, HttpServletRequest request)
   {
      ICheckStickerDAO dao = sqlSession.getMapper(ICheckStickerDAO.class);

      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());
         // int acNo = 23;      // 임시. 나중에 세션에서 받아와야함
         
         // 아래 dao를 실행하기 위해 필요한 변수들 dto에 set해주기
         int checkNo = dao.searchCheckNo(acNo);
         String stickerNo = request.getParameter("stickerNo");
         ArrayList<StickerDTO> list = dao.stickerList(acNo);
         
         // System.out.println(checkNo);
         // System.out.println(stickerNo);
         
         dto.setCheckNo(checkNo);   
         dto.setStickerNo(Integer.parseInt(stickerNo));
         
         dao.putSticker(dto);   // 이 체크리스트에 계정이 가지고 있는 스티커 붙여주기
         
   
         // 이 체크리스트 붙어진 스티커 보여주기
         model.addAttribute("sticker", dao.thisCheck(dao.searchCheckNo(acNo)));
         model.addAttribute("list",list);
      }
      
      // return "ChecklistWrite_third.jsp";
      return "WEB-INF/view/ChecklistWrite_third.jsp";
      
   }
   
   // 새로 스티커를 작성해서 추가하기
   @RequestMapping(value="/addSticker.action", method = RequestMethod.POST)
   public String addSticker(Model model, HttpSession session, StickerDTO dto, HttpServletRequest request)
   {
      ICheckStickerDAO dao = sqlSession.getMapper(ICheckStickerDAO.class);
      
      // 세션 적용
      if (session.getAttribute("userLogin")==null)   // 로그인 안하면 차단
      {
         return "redirect:main.action";
      }
      else
      {
         PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
          
         int acNo = Integer.parseInt(user.getAc_No());      
         // int acNo = 1;      // 임시. 나중에 세션에서 받아와야함
         
         String content = request.getParameter("newSticker");
         
         dto.setAcNo(acNo);
         dto.setContent(content);
         
         dao.addSticker(dto);                     // 이 계정의 스티커 목록에 추가해주기
         
         int stickerNo = dao.searchStickerNo(acNo);      // 방금 추가한 스티커 번호 얻어오기
         int checkNo = dao.searchCheckNo(acNo);         // 체크리스트 번호 얻어오기
         
         // System.out.println(stickerNo);
         // System.out.println(checkNo);
         
         dto.setStickerNo(stickerNo);
         dto.setCheckNo(checkNo);
         
         dao.putSticker(dto);                     // 이 체크리스트에 계정이 가지고 있는 스티커 붙여주기
         
         // 이 체크리스트 붙어진 스티커 보여주기
         model.addAttribute("sticker", dao.thisCheck(dao.searchCheckNo(acNo)));
         model.addAttribute("list",dao.stickerList(acNo));
      }
      
      // return "ChecklistWrite_third.jsp";
      return "WEB-INF/view/ChecklistWrite_third.jsp";
   }
   
   


}

