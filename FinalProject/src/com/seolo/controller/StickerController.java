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

import com.seolo.dto.StickerDTO;
import com.seolo.idao.IStickerModifyDAO;
import com.seolo.personal.PersonalDTO;

@Controller
public class StickerController
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/stickeraddform.action", method = RequestMethod.GET)
	public String addStickerForm(Model model, HttpSession session, HttpServletRequest request)
	{
		String type = request.getParameter("type");
		model.addAttribute("type", type);
		
		// 스티커 리스트 가져오기
		IStickerModifyDAO dao = sqlSession.getMapper(IStickerModifyDAO.class);
		
		// 로그인 안 한 이용자가, 혹은 잘못된 요청으로 접근하려고 한다면
		if (session.getAttribute("userLogin")==null || type==null)
			return "redirect:main.action";
		
		String logAcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
		
		// 1. 본인이 작성한 체크리스트의 스티커 추가 (type=1)
		if (type.equals("1"))
		{
			String checkNo = request.getParameter("checkNo");
			model.addAttribute("checkNo", checkNo);
			
			// 잘못된 요청으로 접근하려고 한다면
			if (checkNo==null)
				return "redirect:main.action";
			
			// 1-1. 체크리스트에 이미 부착되어있는 스티커 목록 가져오기
			ArrayList<String> checkStickerList = dao.listWriterSticker(Integer.parseInt(checkNo));
			model.addAttribute("checkStickerList", checkStickerList);
			
			// 1-2. 내가 가진 스티커들 목록 가져오기
			ArrayList<StickerDTO> stickerList = dao.listSticker(logAcNo);
			model.addAttribute("stickerList", stickerList);
		}
		else if (type.equals("2"))
		// 2. 북마크한 체크리스트의 스티커 추가 (type=2)
		{
			String checkNo = request.getParameter("checkNo");
			model.addAttribute("checkNo", checkNo);
			
			// 잘못된 요청으로 접근하려고 한다면
			if (checkNo==null)
				return "redirect:main.action";
			
			// 2-1. 북마크 넘버 가져오기
			StickerDTO search = new StickerDTO();
			search.setAcNo(Integer.parseInt(logAcNo));
			search.setCheckNo(Integer.parseInt(checkNo));
			
			int chbNo = dao.bookmarkNo(search);
			
			// 2-2. 가져온 북마크 넘버로 체크리스트에 이미 부착되어있는 스티커 목록 가져오기
			ArrayList<String> checkStickerList = dao.listBookmarkSticker(chbNo);
			model.addAttribute("checkStickerList", checkStickerList);
			
			// 2-3. 내가 가진 스티커들 목록 가져오기
			ArrayList<StickerDTO> stickerList = dao.listSticker(logAcNo);
			model.addAttribute("stickerList", stickerList); 
		}
		else if (type.equals("3"))
		// 3. 북마크 지역정보 스티커 추가 (type=3)
		{
			String dongNo = request.getParameter("dongNo");
			model.addAttribute("dongNo", dongNo);
			
			// 잘못된 요청으로 접근하려고 한다면
			if (dongNo==null)
				return "redirect:main.action";
			
			// 3-1. 북마크 넘버 가져오기
			StickerDTO search = new StickerDTO();
			search.setAcNo(Integer.parseInt(logAcNo));
			search.setDongNo(dongNo);
			
			int lobNo = dao.localBookmarkNo(search);
			
			// 3-2. 북마크 지역정보에 부착되어 있는 스티커 목록 가져오기
			ArrayList<String> localStickerList = dao.listLocalBookmarkSticker(lobNo);
			model.addAttribute("localStickerList", localStickerList);
			
			// 3-3. 내가 가진 스티커들 목록 가져오기
			ArrayList<StickerDTO> stickerList = dao.listSticker(logAcNo);
			model.addAttribute("stickerList", stickerList); 
		}
		
		return "WEB-INF/view/StickerAdd.jsp";
	}
	
	@RequestMapping(value = "/stickeradd.action", method = RequestMethod.GET)
	public String addSticker(Model model, HttpSession session, HttpServletRequest request)
	{
		// 기존 스티커 목록에서 스티커 추가하기

		String stickerNo = request.getParameter("stickerNo");
		String type = request.getParameter("type");
		
		IStickerModifyDAO dao = sqlSession.getMapper(IStickerModifyDAO.class);
		
		// 로그인 안 한 이용자가, 혹은 잘못된 요청으로 접근하려고 한다면
		if (session.getAttribute("userLogin")==null || type==null)
			return "redirect:main.action";
		
		if (type.equals("1"))	// 작성한 체크리스트
		{
			String checkNo = request.getParameter("checkNo");
			
			int tagNo = dao.myCheckTagNo();
			StickerDTO stkAdd = new StickerDTO();
			stkAdd.setStickerNo(Integer.parseInt(stickerNo));
			stkAdd.setCheckNo(Integer.parseInt(checkNo));
			stkAdd.setTagNo(tagNo);
			
			dao.addMyCheckSticker(stkAdd);
			
			return "redirect:stickeraddform.action?checkNo="+checkNo+"&type="+type;
		}
		else if (type.equals("2"))	// 북마크 체크리스트
		{
			String checkNo = request.getParameter("checkNo");
			
			int tagNo = dao.checkTagNo();
			// 북마크 넘버 가져오기
			String logAcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
			StickerDTO search = new StickerDTO();
			search.setAcNo(Integer.parseInt(logAcNo));
			search.setCheckNo(Integer.parseInt(checkNo));
			int chbNo = dao.bookmarkNo(search);
			
			StickerDTO stkAdd = new StickerDTO();
			stkAdd.setStickerNo(Integer.parseInt(stickerNo));
			stkAdd.setChbNo(chbNo);
			stkAdd.setTagNo(tagNo);
			
			dao.addCheckSticker(stkAdd);
			
			return "redirect:stickeraddform.action?checkNo="+checkNo+"&type="+type;
		}
		else if (type.equals("3"))
		{
			String dongNo = request.getParameter("dongNo");
			
			int tagNo = dao.localTagNo();
			// 북마크 넘버 가져오기
			String logAcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
			StickerDTO search = new StickerDTO();
			search.setAcNo(Integer.parseInt(logAcNo));
			search.setDongNo(dongNo);
			int lobNo = dao.localBookmarkNo(search);
			
			StickerDTO stkAdd = new StickerDTO();
			stkAdd.setStickerNo(Integer.parseInt(stickerNo));
			stkAdd.setLobNo(lobNo);
			stkAdd.setTagNo(tagNo);
			
			dao.addLocalSticker(stkAdd);
			
			return "redirect:stickeraddform.action?dongNo="+dongNo+"&type="+type;
		}
		
		return "redirect:main.action";
	}
	
	@RequestMapping(value = "/addnewsticker.action", method = RequestMethod.POST)
	public String newSticker(Model model, HttpSession session, HttpServletRequest request)
	{
		// 새 스티커 입력해서 추가하기 
		
		String type = request.getParameter("type");
		String content = request.getParameter("newSticker");
		
		IStickerModifyDAO dao = sqlSession.getMapper(IStickerModifyDAO.class);
		// 로그인 안 한 이용자가, 혹은 잘못된 요청으로 접근하려고 한다면
		if (session.getAttribute("userLogin")==null || type==null)
			return "redirect:main.action";	
		
		String logAcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
		
		int stickerNo = dao.maxStickerNo();
		StickerDTO sticker = new StickerDTO();
		sticker.setContent(content);
		sticker.setStickerNo(stickerNo);
		sticker.setAcNo(Integer.parseInt(logAcNo));
		
		dao.addNewSticker(sticker);	// 스티커 테이블에 먼저 스티커 추가
		
		if (type.equals("1"))	// 본인이 작성한 ckl
		{
			String checkNo = request.getParameter("checkNo");
			
			// 내 체크리스트 부착 스티커 테이블에 스티커 추가
			int tagNo = dao.myCheckTagNo();
			sticker.setCheckNo(Integer.parseInt(checkNo));
			sticker.setTagNo(tagNo);
			
			dao.addMyCheckSticker(sticker);
			return "redirect:stickeraddform.action?checkNo="+checkNo+"&type="+type;
		}
		else if (type.equals("2"))	// 북마크 ckl
		{
			String checkNo = request.getParameter("checkNo");
			
			// 체크리스트 부착 스티커 테이블에 스티커 추가
			
			// 북마크 넘버 가져오기
			sticker.setCheckNo(Integer.parseInt(checkNo));
			int chbNo = dao.bookmarkNo(sticker);
			
			int tagNo = dao.checkTagNo();
			sticker.setChbNo(chbNo);
			sticker.setTagNo(tagNo);
			
			dao.addCheckSticker(sticker);
			return "redirect:stickeraddform.action?checkNo="+checkNo+"&type="+type;
		}
		else if (type.equals("3"))
		{
			String dongNo = request.getParameter("dongNo");
			
			// 지역정보 부착 스티커에 테이블에 스티커 추가
			
			// 북마크 넘버 가져오기
			sticker.setDongNo(dongNo);
			int lobNo = dao.localBookmarkNo(sticker);
			
			int tagNo = dao.localTagNo();
			sticker.setLobNo(lobNo);
			sticker.setTagNo(tagNo);
			
			dao.addLocalSticker(sticker);
			return "redirect:stickeraddform.action?dongNo="+dongNo+"&type="+type;
		}
		
		return "redirect:main.action";
	}
	
	@RequestMapping(value = "/stickerdeleteform.action", method = RequestMethod.GET)
	public String deleteStickerForm(Model model, HttpSession session, HttpServletRequest request)
	{
		String type = request.getParameter("type");
		model.addAttribute("type", type);
		
		IStickerModifyDAO dao = sqlSession.getMapper(IStickerModifyDAO.class);
		
		// 로그인 안 한 이용자가, 혹은 잘못된 요청으로 접근하려고 한다면
		if (session.getAttribute("userLogin")==null || type==null)
		{
			return "redirect:main.action";
		}
		
		String logAcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
		
		// 1. 본인이 작성한 체크리스트의 스티커 추가 (type=1)
		if (type.equals("1"))
		{
			String checkNo = request.getParameter("checkNo");
			model.addAttribute("checkNo", checkNo);
			
			// 잘못된 요청으로 접근하려고 한다면
			if (checkNo==null)
				return "redirect:main.action";
			
			// 1-1. 체크리스트에 이미 부착되어있는 스티커 목록 가져오기
			ArrayList<StickerDTO> checkStickerList = dao.deleteListWriterSticker(Integer.parseInt(checkNo));
			model.addAttribute("checkStickerList", checkStickerList);
		}
		else if (type.equals("2"))
		// 2. 북마크한 체크리스트의 스티커 추가 (type=2)
		{
			String checkNo = request.getParameter("checkNo");
			model.addAttribute("checkNo", checkNo);
			
			// 잘못된 요청으로 접근하려고 한다면
			if (checkNo==null)
				return "redirect:main.action";
			
			// 2-1. 북마크 넘버 가져오기
			StickerDTO search = new StickerDTO();
			search.setAcNo(Integer.parseInt(logAcNo));
			search.setCheckNo(Integer.parseInt(checkNo));
			
			int chbNo = dao.bookmarkNo(search);
			
			// 2-2. 가져온 북마크 넘버로 체크리스트에 이미 부착되어있는 스티커 목록 가져오기
			ArrayList<StickerDTO> checkStickerList = dao.deleteListBookmarkSticker(chbNo);
			model.addAttribute("checkStickerList", checkStickerList);
		}
		else if (type.equals("3"))
		// 3. 북마크한 지역정보의 스티커 추가 (type=3)
		{
			String dongNo = request.getParameter("dongNo");
			model.addAttribute("dongNo", dongNo);
			
			// 잘못된 요청으로 접근하려고 한다면
			if (dongNo==null)
				return "redirect:main.action";
			
			// 3-1. 북마크 넘버 가져오기
			StickerDTO search = new StickerDTO();
			search.setAcNo(Integer.parseInt(logAcNo));
			search.setDongNo(dongNo);
			
			int lobNo = dao.localBookmarkNo(search);
			
			// 3-2. 북마크 지역정보에 부착되어 있는 스티커 목록 가져오기
			ArrayList<StickerDTO> localStickerList = dao.deleteListLocalBookmarkSticker(lobNo);
			model.addAttribute("localStickerList", localStickerList);
		}
		
		return "WEB-INF/view/StickerDelete.jsp";
	}
	
	@RequestMapping(value = "/stickerdelete.action", method = RequestMethod.GET)
	public String deleteSticker(Model model, HttpSession session, HttpServletRequest request)
	{
		String type = request.getParameter("type");
		
		String tagNo = request.getParameter("tagNo");
		// 로그인 안 한 이용자가, 혹은 잘못된 요청으로 접근하려고 한다면
		if (session.getAttribute("userLogin")==null || tagNo==null)
			return "redirect:main.action";
		
		IStickerModifyDAO dao = sqlSession.getMapper(IStickerModifyDAO.class);
		
		if (type.equals("1"))
		{
			// 내 체크리스트라면
			String checkNo = request.getParameter("checkNo");
			
			// tagNo를 통해서 cstickerNo 알아내기
			int stickerNo = dao.writerStickerNo(tagNo);
			
			// tagNo를 통해서 부착된 스티커 지우기
			dao.deleteWriterSticker(Integer.parseInt(tagNo));
			
			// 더이상 사용하지 않는 스티커라면 스티커 테이블에서 지우기
			int count = dao.validSticker(stickerNo);
			if (count==0)
				dao.deleteInvalidSticker(stickerNo);
			
			return "redirect:stickerdeleteform.action?checkNo="+checkNo+"&type="+type;
		}
		else if (type.equals("2"))
		{
			// 북마크 체크리스트라면
			String checkNo = request.getParameter("checkNo");
			
			// tagNo를 통해서 cstickerNo 알아내기
			int stickerNo = dao.bookmarkStickerNo(tagNo);
			
			// tagNo를 통해서 부착된 스티커 지우기
			dao.deleteBookmarkSticker(Integer.parseInt(tagNo));
			
			// 더이상 사용하지 않는 스티커라면 스티커 테이블에서 지우기
			int count = dao.validSticker(stickerNo);
			if (count==0)
				dao.deleteInvalidSticker(stickerNo);
			
			return "redirect:stickerdeleteform.action?checkNo="+checkNo+"&type="+type;
		}
		else if (type.equals("3"))
		{
			// 북마크 지역정보라면
			String dongNo = request.getParameter("dongNo");
			
			// tagNo를 통해서 cstickerNo 알아내기
			int stickerNo = dao.localStickerNo(tagNo);
			
			// tagNo를 통해서 부착된 스티커 지우기
			dao.deleteLocalSticker(Integer.parseInt(tagNo));
			
			// 더이상 사용하지 않는 스티커라면 스티커 테이블에서 지우기
			int count = dao.validSticker(stickerNo);
			if (count==0)
				dao.deleteInvalidSticker(stickerNo);
			
			return "redirect:stickerdeleteform.action?dongNo="+dongNo+"&type="+type;
		}
		
		return "redirect:main.action";
	}

}
