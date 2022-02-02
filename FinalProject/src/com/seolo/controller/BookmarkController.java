/*==========================
   BookmarkController.java
   - 북마크 관련 컨트롤러
============================*/

package com.seolo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import com.seolo.dto.BookmarkDTO;
import com.seolo.dto.StickerDTO;
import com.seolo.idao.IBookmarkDAO;
import com.seolo.personal.PersonalDTO;

@Controller
public class BookmarkController
{
	/// mybatis 객체 의존성 (자동) 주입~!!!
	@Autowired
	private SqlSession sqlSession;
	
	// 지역 북마크 페이지로 이동 (ReadLocal.jsp로부터)
	@RequestMapping(value = "/addLocalBookmark.action", method = RequestMethod.GET)
	public String localBookmarkform(Model model, HttpSession session, String dongNo, HttpServletRequest request)
	{
		IBookmarkDAO dao = sqlSession.getMapper(IBookmarkDAO.class);
		
        // 세션 적용
        if(session.getAttribute("userLogin")==null)
        {
        	return "redirect:main.action";
        }
        
    	PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
    	
    	int acNo = Integer.parseInt(user.getAc_No());
    	// System.out.println("acNo1 : " + acNo);
	
		ArrayList<String> list = dao.stickerList(acNo);
		
		// System.out.println("dongNo1 : " +  dongNo );
		
		model.addAttribute("dongNo", dongNo);
		model.addAttribute("list", list);
		
		// return "WEB-INF/view/AddLocalBookmark.jsp";
		return "AddLocalBookmark.jsp";
	}
	
	
	// 지역 : 기존의 스티커를 선택하면 선택한 스티커에 추가
	@RequestMapping(value="/addLocalSticker.action", method = RequestMethod.GET)
	public String addLocalSticker(Model model, HttpSession session, String stickerNo, String dongNo)
	{
		IBookmarkDAO dao = sqlSession.getMapper(IBookmarkDAO.class);
		
        // 세션 적용
        if(session.getAttribute("userLogin")==null)
        {
        	return "redirect:main.action";
        }
        
    	PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
    	
    	int acNo = Integer.parseInt(user.getAc_No());
    	
    	// System.out.println("acNo : " + acNo);
		// System.out.println("dongNo : " + dongNo);
		// System.out.println("stickerNo : " + stickerNo);
		
		
		// 북마크 DTO에 set해주기
		BookmarkDTO bdto = new BookmarkDTO();
		bdto.setAcNo(String.valueOf(acNo));
		bdto.setDongNo(dongNo);
		bdto.setCstickerNo(Integer.parseInt(stickerNo));
		
		if( dao.localbookmarkornot(bdto)==null )		// 지역정보 북마크가 안 되어있을 경우
		{
			dao.addLocalBookmark(bdto);		// 지역정보 북마크하기
			
			// 북마크DTO에 지역북마크번호 set
			int lobNo = dao.searchLocalBookNo(acNo);
			bdto.setLobNo(lobNo);
			// System.out.println("lobNo : " + lobNo);
			
			dao.addLocalSticker(bdto);		// 지역북마크에 스티커 부착
		}
		else								// 지역정보 북마크가 되어있을 경우
		{
			// 북마크DTO에 지역북마크번호 set
			int lobNo = dao.searchLocalBookNo(acNo);
			bdto.setLobNo(lobNo);
			// System.out.println("lobNo : " + lobNo);
			
			dao.addLocalSticker(bdto);		// 지역북마크에 스티커 부착
		}
        
		// 이 북마크에 붙은 스티커(선택한 스티커) 보여주기
		ArrayList<StickerDTO> sticker = dao.localStickerList(acNo, dongNo);
		// 내가 작성한 스티커 보여주기
		ArrayList<String> list = dao.stickerList(acNo);
		
		model.addAttribute("dongNo", dongNo);
		model.addAttribute("sticker", sticker);
		model.addAttribute("list", list);
		
		// return "WEB-INF/view/AddLocalBookmark.jsp";
		return "AddLocalBookmark.jsp"; 
	}
	
	
	// 지역 : 새로 스티커를 추가하면 선택한 스티커에 추가
	// 지역정보가 북마크 안되어 있으면 북마크한 다음 스티커 추가하는 방식
	@RequestMapping(value="/addNewLocalSticker.action", method=RequestMethod.GET)
	public String addNewLocalSticker(Model model, HttpSession session, String dongNo, String newSticker)
	{
		IBookmarkDAO dao = sqlSession.getMapper(IBookmarkDAO.class);
		
        // 세션 적용
        if(session.getAttribute("userLogin")==null)
        {
        	return "redirect:main.action";
        }
 
    	PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
    	
    	// System.out.println("새 스티커 추가 acNo : " + user.getAc_No());
    	// System.out.println(dongNo);
    	// System.out.println(newSticker);
    	
    	BookmarkDTO dto = new BookmarkDTO();
    	dto.setAcNo(user.getAc_No());
    	dto.setDongNo(dongNo);
    	
    	StickerDTO sdto = new StickerDTO();
    	sdto.setAcNo(Integer.parseInt(user.getAc_No()));
    	sdto.setContent(newSticker);
    	
		// 내 스티커에 추가하기 - content, acNo
		dao.addSticker(sdto);
    	
    	if(dao.localbookmarkornot(dto)==null)	// 북마크가 안 되어있을 경우
    	{
    		// 지역정보 북마크하기 - dongNo, acNo
    		dao.addLocalBookmark(dto);
    	}
    		
		
    	// 북마크 DTO에 지역북마크 번호, 추가한 스티커번호 set
		int lobNo = dao.searchLocalBookNo(Integer.parseInt(user.getAc_No()));
		dto.setLobNo(lobNo);
		// System.out.println("새 스티커 추가 lobNo : " + lobNo);
		
		StickerDTO s = dao.searchSticker(Integer.parseInt(user.getAc_No()));
		dto.setCstickerNo(s.getStickerNo());
		
		// 북마크에 추가한 스티커 붙이기
		dao.addLocalSticker(dto);
    		
		// 이 북마크에 붙은 스티커(선택한 스티커) 보여주기
		ArrayList<StickerDTO> sticker = dao.localStickerList(Integer.parseInt(user.getAc_No()), dongNo);
		// 내가 작성한 스티커 보여주기
		ArrayList<String> list = dao.stickerList(Integer.parseInt(user.getAc_No()));
		
		model.addAttribute("dongNo", dongNo);
		model.addAttribute("sticker", sticker);
		model.addAttribute("list", list);
		
		// return "WEB-INF/view/AddLocalBookmark.jsp";
		return "AddLocalBookmark.jsp";
	}
	
	
	/*
	// 지역 북마크 삭제하기(ReadLocal.jsp로부터)
	@RequestMapping(value="/removeLocalSticker.action", method=RequestMethod.GET)
	public String removeLocalBookmark(Model model, String dongNo, HttpSession session)
	{
		IBookmarkDAO dao = sqlSession.getMapper(IBookmarkDAO.class);

        // 세션 적용
        if(session.getAttribute("userLogin")==null)
        {
        	return "redirect:main.action";
        }
 
    	PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
    	
    	// String acNo = user.getAc_No();
    	
    	BookmarkDTO dto = new BookmarkDTO();
    	
    	dto.setAcNo(user.getAc_No());
    	dto.setDongNo(dongNo);
    	
    	// System.out.println("지역북마크삭제 acNo : " +  user.getAc_No());
    	// System.out.println("지역북마크삭제 dongNo : " + dongNo );
    	
    	int lobNo = Integer.parseInt(dao.localbookmarkornot(dto));
    	
    	// System.out.println("지역북마크삭제 lobNo : " + lobNo);
    	
    	dto.setLobNo(lobNo);	//  북마크 고유번호 가져온다.

    	// System.out.println("tag remove 전");
    	dao.removeLocaltag(dto);	
    	
    	// System.out.println("bookmark remove 전");
    	dao.removeLocalBookmark(dto);
		
    	// System.out.println("return 전");
    	
		return "redirect:main.action";
	}
	
	
	//========================= 여기부터 체크리스트 북마크 =========================
	
	
	// 체크 북마크 페이지로 이동 (ReadChecklist.jsp로부터)
	@RequestMapping(value="/addCheckBookmark.action", method=RequestMethod.GET)
	public String checkBookmarkform(Model model, HttpSession session, String checkNo)
	{
		IBookmarkDAO dao = sqlSession.getMapper(IBookmarkDAO.class);

        // 세션 적용
        if(session.getAttribute("userLogin")==null)
        {
        	return "redirect:main.action";
        }
 
    	PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");

    	// System.out.println("체크리스트 북마크 acNo : " + user.getAc_No());
    	// System.out.println("체크리스트 북마크 checkNo : " + checkNo);
    	
    	// 이 계정에 해당하는 스티커 리스트
		ArrayList<String> list = dao.stickerList(Integer.parseInt(user.getAc_No()));
		
		model.addAttribute("list", list);
    	model.addAttribute("checkNo", checkNo);
		
		// return "WEB-INF/view/AddCheckBookmark.jsp";
		return "AddCheckBookmark.jsp";
	}
	*/
	
	// 체크 : 기존의 스티커를 선택하면 선택한 스티커에 추가
	//@ title 이 안넘어가는 문제 해결
	@RequestMapping(value="/addCheckSticker.action", method=RequestMethod.GET)
	public String addCheckSticker(Model model, HttpSession session, String checkNo, int stickerNo, String title)
	{
		IBookmarkDAO dao = sqlSession.getMapper(IBookmarkDAO.class);

        // 세션 적용
        if(session.getAttribute("userLogin")==null)
        {
        	return "redirect:main.action";
        }
 
    	PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
    	
    	BookmarkDTO dto = new BookmarkDTO();
    	dto.setAcNo(user.getAc_No());
    	dto.setCheckNo(Integer.parseInt(checkNo));
    	// dto.setTitle(title);
    	dto.setCstickerNo(stickerNo);
    	
    	System.out.println("체크 acNo : " + user.getAc_No());
    	System.out.println("체크 checkNo : " + checkNo);
    	// System.out.println("체크 title : " + title);
    	System.out.println("체크 stickerNo : " + stickerNo);
    	
    	// 체크리스트 북마크가 안 되어 있을 경우
    	if(dao.checkbookmarkornot(dto)==null)
    	{
    		// 체크리스트 북마크 하기
    		dao.addCheckBookmark(dto);
    	}
    	
    	// 북마크 DTO에 체크북마크 번호 set
    	int chbNo = dao.searchCheckBookNo(Integer.parseInt(user.getAc_No()));
    	dto.setChbNo(chbNo);
    	System.out.println("체크 chbNo : " + chbNo);
    	
    	// 체크북마크에 스티커 붙이기
    	dao.addCheckSticker(dto);
    	
    	
    	// 북마크에 붙은 스티커(선택한 스티커) 보여주기
    	ArrayList<StickerDTO> sticker = dao.checkStickerList(Integer.parseInt(user.getAc_No()), Integer.parseInt(checkNo));
    	
    	// 내가 작성한 스티커 보여주기
    	ArrayList<String> list = dao.stickerList(Integer.parseInt(user.getAc_No()));
		
    	model.addAttribute("checkNo", checkNo);
    	model.addAttribute("sticker", sticker);
    	model.addAttribute("list", list);
    	
		// return "WEB-INF/view/AddCheckBookmark.jsp";
		return "AddCheckBookmark.jsp";
	}
	
	
	// 체크 : 새로 스티커를 추가하면 선택한 스티커에 추가
	//@ title 이 안넘어가는 부분 문제
	@RequestMapping(value="/addNewCheckSticker.action", method=RequestMethod.GET)
	public String addNewCheckSticker(Model model, HttpSession session, String checkNo, int stickerNo, String title, String newSticker)
	{
		IBookmarkDAO dao = sqlSession.getMapper(IBookmarkDAO.class);

        // 세션 적용
        if(session.getAttribute("userLogin")==null)
        {
        	return "redirect:main.action";
        }
 
    	PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
    	
    	// 북마크 DTO, 스티커 DTO 채우기
    	BookmarkDTO dto = new BookmarkDTO();
    	dto.setAcNo(user.getAc_No());
    	dto.setCheckNo(Integer.parseInt(checkNo));
    	dto.setTitle(title);
    	
    	StickerDTO sdto = new StickerDTO();
    	sdto.setAcNo(Integer.parseInt(user.getAc_No()));
    	sdto.setContent(newSticker);
    	
    	System.out.println("새 체크 acNo : " + user.getAc_No());
    	System.out.println("새 체크 checkNo : " + checkNo);
    	System.out.println("새 체크 title : " + title);
    	System.out.println("새 체크 newSticker : " + newSticker);
    	
    	
    	// 내 스티커에 추가하기
    	dao.addSticker(sdto);
    	
    	// 북마크가 안 되어있을 경우
    	if(dao.checkbookmarkornot(dto)==null)
    	{
    		// 체크리스트 북마크하기
    		dao.addCheckBookmark(dto);
    	}
    	
    	// 북마크 DTO에 체크북마크 번호, 추가한 스티커 번호 set
		int chbNo = dao.searchCheckBookNo(Integer.parseInt(user.getAc_No()));
		dto.setChbNo(chbNo);
		System.out.println("새 체크 chbNo : " + chbNo);
		
		StickerDTO s = dao.searchSticker(Integer.parseInt(user.getAc_No()));
		dto.setCstickerNo(s.getStickerNo());
		System.out.println("새 체크 stickerNo : " + stickerNo);
		
		// 북마크에 추가한 스티커 붙이기
		dao.addCheckSticker(dto);
		 
		// 북마크에 붙은 스티커(선택한 스티커) 보여주기
    	ArrayList<StickerDTO> sticker = dao.checkStickerList(Integer.parseInt(user.getAc_No()), Integer.parseInt(checkNo));
    	
    	// 내가 작성한 스티커 보여주기
    	ArrayList<String> list = dao.stickerList(Integer.parseInt(user.getAc_No()));
		
    	model.addAttribute("checkNo", checkNo);
    	model.addAttribute("sticker", sticker);
    	model.addAttribute("list", list);
    	
		
		// return "WEB-INF/view/AddCheckBookmark.jsp";
		return "AddCheckBookmark.jsp";
	}
	
	/*
	// 체크 북마크 삭제하기 (ReadChecklist.jsp로부터)
	@RequestMapping(value="/removeCheckSticker.action", method=RequestMethod.GET)
	public String removeCheckBookmark(Model model, HttpSession session, String checkNo)
	{
		IBookmarkDAO dao = sqlSession.getMapper(IBookmarkDAO.class);

        // 세션 적용
        if(session.getAttribute("userLogin")==null)
        {
        	return "redirect:main.action";
        }
 
    	PersonalDTO user = (PersonalDTO)session.getAttribute("userLogin");
    	
    	BookmarkDTO dto = new BookmarkDTO();
    	dto.setAcNo(user.getAc_No());
    	dto.setCheckNo(Integer.parseInt(checkNo));

    	System.out.println("체크북마크 삭제 checkNo : " + checkNo);
    	System.out.println("체크북마크 삭제 acNo : " + user.getAc_No());
    	
    	int chbNo = Integer.parseInt(dao.checkbookmarkornot(dto));
    	
    	System.out.println("체크북마크 삭제 chbNo : " + chbNo);
    	
    	dto.setChbNo(chbNo);
    	
    	dao.removeChecktag(dto);
    	dao.removeCheckBookmark(dto);
		
		return "redirect:main.action";
	}
	*/
}
