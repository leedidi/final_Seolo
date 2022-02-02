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

import com.seolo.admin.INoticeDAO;
import com.seolo.dto.BookmarkDTO;
import com.seolo.dto.ChecklistDTO;
import com.seolo.dto.LocalDTO;
import com.seolo.dto.PlusDTO;
import com.seolo.idao.IReadDAO;
import com.seolo.personal.PersonalDTO;

@Controller
public class ChecklistReadController
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/readcheck.action", method = RequestMethod.GET)
	public String readCheck(Model model, HttpSession session, HttpServletRequest request)
	{
		IReadDAO dao = sqlSession.getMapper(IReadDAO.class);
		
		String tempNo = request.getParameter("checkNo");
		if (tempNo == null)
		{
			return "redirect:main.action";	// 개별 체크리스트 조회페이지는 체크리스트 번호가 없으면 조회 불가
		}
		
		int checkNo = Integer.parseInt(tempNo);
		ChecklistDTO checklist = null;
		checklist = dao.read(checkNo);
		

		if (checklist==null)	// 존재하지 않는 체크리스트번호를 조회하려고 하면
		{
			return "redirect:main.action";
		}
		else
		{
			// ⓐ. 작성자 = 로그인한 사람?
			// → 맞다 : 주소, 제목, 비밀코멘트 / 수정, 삭제버튼O / 스티커조회O
			// ⓑ. 아니라면 북마크했는지?
			// → 맞다 : 주소, 제목, 비밀코멘트 X / 수정, 삭제버튼X / 스티커조회O / 북마크 삭제
			// ⓒ. 그냥 로그인한 사람
			// → 주소, 제목, 비밀코멘트 X / 수정, 삭제버튼X / 스티커조회X / 북마크 추가
			// ⓓ. 비로그인
			// → 주소, 제목, 비밀코멘트 X / 수정, 삭제버튼X / 스티커조회X / 북마크 추가,삭제X
			
			
			// 체크리스트 작성자 = 로그인한 사람인지 확인하기
			// 1) 로그인했는지?
			if (session.getAttribute("userLogin")!=null)	// 로그인했다
			{
				// 2) 로그인한 사람 = 체크리스트 작성자?
				String logAcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();	// 로그인한 사람의 acNo
				int acNo = checklist.getAcNo();	// 체크리스트 작성자의 acNo
				
				if (acNo == Integer.parseInt(logAcNo))	// 작성자=로그인한 사람 → ⓐ
				{
					model.addAttribute("user", "writer");
					// 스티커 리스트
					ArrayList<String> stickerList = dao.listWriterSticker(checkNo);
					if (stickerList!=null)
						model.addAttribute("stickerList", stickerList);
					
				}
				else	// 작성자!=로그인한 사람
				{
					BookmarkDTO bookMark = dao.isBookMark(new BookmarkDTO(logAcNo, checkNo));
					
					// 3) 북마크 했는지?
					if (bookMark!=null)	// 북마크 했다 → ⓑ
					{
						model.addAttribute("user", "bookmarker");
						model.addAttribute("bookMark", bookMark);
						
						// 스티커 리스트
						ArrayList<String> stickerList = dao.listBookmarkSticker(bookMark.getChbNo());
						if (stickerList!=null)
							model.addAttribute("stickerList", stickerList);
						
						
					}
					else	// 북마크 안했다 → ⓒ
					{
						model.addAttribute("user", "viewer");
					}
				}
				
				String ulogAcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
				model.addAttribute("ulogAcNo", ulogAcNo);
				//System.out.println(ulogAcNo);
				
			}
			else	// 비로그인 → ⓓ
			{
				String ulogAcNo = "";
				model.addAttribute("ulogAcNo", ulogAcNo);	
				//System.out.println(ulogAcNo);
				model.addAttribute("user", "");
			
			}
			
			// 추가항목 확인하기
			ArrayList<PlusDTO> timeList = dao.listTime(checkNo);
			ArrayList<PlusDTO> scoreList = dao.listScore(checkNo);
			if (timeList != null)
				model.addAttribute("timeList", timeList);
			if (scoreList != null)
				model.addAttribute("scoreList", scoreList);
			
			
			model.addAttribute("checklist", checklist);
			model.addAttribute("scores", dao.scoreList());
			
			return "WEB-INF/view/ReadChecklist.jsp";
		}
		
	}
	
	// 북마크 지역정보 조회
	// 지역정보 삭제 위해 "method = {RequestMethod.GET, RequestMethod.POST}" 처럼 POST 방식도 사용하도록 추가 
	@RequestMapping(value = "/readlocal.action",  method = {RequestMethod.GET, RequestMethod.POST})
	public String readLocal(Model model, HttpSession session, HttpServletRequest request)
	{
		IReadDAO dao = sqlSession.getMapper(IReadDAO.class);
		
		String dongNo = request.getParameter("dongNo");
		if (dongNo == null)
		{
			return "redirect:main.action";	// 개별 지역정보 조회페이지는 지역번호가 없으면 조회 불가
		}
		
		LocalDTO localList = null;		
		localList = dao.readLocal(dongNo);
		if (localList==null)	// 존재하지 않는 지역번호를 조회하려고 하면
		{
			return "redirect:main.action";
		}
		else
		{
			// 로그인 했는지?
			if (session.getAttribute("userLogin")!=null)
			{
				String acNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();	// 로그인한 사람의 acNo
				BookmarkDTO bookMark = dao.isLocalBookMark(new BookmarkDTO(acNo, dongNo));
				
				// 북마크 추가했는지? 확인하기
				if (bookMark!=null)	// 북마크 했음
				{
					model.addAttribute("user", "bookmarker");
					model.addAttribute("bookMark", bookMark);
					
					// 스티커 리스트
					ArrayList<String> stickerList = dao.listLocalBookmarkSticker(bookMark.getLobNo());
					if (stickerList!=null)
						model.addAttribute("stickerList", stickerList);
					
				}
				else // 북마크 안했음
				{
					model.addAttribute("user", "viewer");
				}
				
			}
			
			model.addAttribute("dongNo", dongNo);
			model.addAttribute("localList", localList);
			
			return "WEB-INF/view/ReadLocal.jsp";
		}
	
	}
	

	// 북마크 수정하기 폼으로 이동
	@RequestMapping(value = "/updatebookmarkcheck.action", method = RequestMethod.GET)
	public String UpdateBookmarkform(Model model, HttpSession session, HttpServletRequest request)
	{
		IReadDAO dao = sqlSession.getMapper(IReadDAO.class);
				
		// 체크리스트 정보 받아옴
		int checkNo = Integer.parseInt(request.getParameter("checkNo"));
		ChecklistDTO cDto = dao.read(checkNo);	

		model.addAttribute("checklist", cDto);
		
		// 북마크 정보 받아옴
		String AcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
		BookmarkDTO bDto = dao.isBookMark(new BookmarkDTO(AcNo, checkNo));
		
		model.addAttribute("user", "bookmarker");
		model.addAttribute("bookMark", bDto);
		
		return "WEB-INF/view/UpdateBookmarkChecklist.jsp";
	}
	
	
	// 북마크 체크리스트 수정하기 기능
	@RequestMapping(value = "/updatechecking.action", method = RequestMethod.GET)
	public String UpdateBookmark(Model model, HttpSession session, HttpServletRequest request)
	{
		IReadDAO dao = sqlSession.getMapper(IReadDAO.class);
		
		// 체크리스트 정보 받아옴
		int checkNo = Integer.parseInt(request.getParameter("checkNo"));
		ChecklistDTO cDto = dao.read(checkNo);	

		model.addAttribute("checklist", cDto);
		
		// 북마크 정보 받아옴
		String AcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
		BookmarkDTO bDto = dao.isBookMark(new BookmarkDTO(AcNo, checkNo));
		
		model.addAttribute("user", "bookmarker");
		model.addAttribute("bookMark", bDto);
		
		// 수정 기능 추가
		// 임의로 title 입력 정보 받아옴
		String title = request.getParameter("title");
		
		// title과 AcNo → BookmarkDTO에 넣어줌
		bDto.setTitle(title);
		bDto.setAcNo(AcNo);
		
		dao.updateCheckBookMark(bDto);
		
		// 해당 북마크 체크리스트로 이동
		return "redirect:readcheck.action?checkNo=" + checkNo;
	}
	
	// 북마크 체크리스트 삭제하기 기능
	@RequestMapping(value = "/deletechecking.action", method = RequestMethod.GET)
	public String DeleteBookmark(BookmarkDTO dto, HttpSession session, HttpServletRequest request)
	{
		IReadDAO dao = sqlSession.getMapper(IReadDAO.class);
		
		// AcNO 찾아서 BookMarkDTO에 넣어주기
		String AcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
		dto.setAcNo(AcNo);
		
		// 북마크 삭제 위한 chbNo
		BookmarkDTO cDto = dao.isBookMark(dto);
		int chbNo = cDto.getChbNo();
		dto.setChbNo(chbNo);
		
		// 북마크 스티커 테이블 역시 삭제
		ArrayList<String> checkSticker = dao.selectCheckStiker(chbNo);
		
		// 값 하나씩 증가하며 arraylist에 들어있는 csticker_no 가 해당되는 숫자인 sticker 테이블의 해당 값 지워줌
		for (int i=0; i<checkSticker.size(); i++)
		{
			// 만약 해당 스티커가(csticker_no가 해당 북마크 체크리스트에서만 사용된다면 북마크 삭제 시 해당 스티커도 삭제
			if (dao.selectStikerCheckOne(Integer.parseInt(checkSticker.get(i)))==1)
			{
				// 해당 i를 dto csticker_no에 넣어줌 
				dto.setCstickerNo(Integer.parseInt(checkSticker.get(i)));
				// 해당 스티커를 제거해줌
				dao.deleteSticker(dto);
			}
		}
		
		// 북마크 체크리스트 및 스티커 삭제
		dao.deleteCheckBookMarkSticker(cDto);
		dao.deleteCheckBookMark(dto);
		
		// 체크리스트 삭제 후 해당 체크리스트 조회하기 위한 checkNo
		int checkNo = Integer.parseInt(request.getParameter("checkNo"));
		
		// 해당 북마크 체크리스트로 이동
		return "redirect:readcheck.action?checkNo=" + checkNo;
	}
	
	
	// 북마크 지역정보 삭제하기 기능
	@RequestMapping(value = "/deletebookmarklocal.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String Deletebookmarklocal(BookmarkDTO dto, HttpSession session, HttpServletRequest request)
	{
		IReadDAO dao = sqlSession.getMapper(IReadDAO.class);
		
		// AcNO 찾아서 BookMarkDTO에 넣어주기
		String AcNo = ((PersonalDTO)session.getAttribute("userLogin")).getAc_No();
		dto.setAcNo(AcNo);
		
		// 북마크 스티커 삭제 위한 lobNo
		BookmarkDTO lDto = dao.isLocalBookMark(dto);
		int lobNo = lDto.getLobNo();
    	dto.setLobNo(lobNo);

		// 북마크 스티커 테이블 역시 삭제
		ArrayList<String> checkSticker = dao.selectLocalStiker(lobNo);
		
		// 값 하나씩 증가하며 arraylist에 들어있는 csticker_no 가 해당되는 숫자인 sticker 테이블의 해당 값 지워주기
		for (int i=0; i<checkSticker.size(); i++)
		{
			// 만약 해당 스티커가(csticker_no가 해당 북마크 지역정보에서만 사용된다면 북마크 삭제 시 해당 스티커도 삭제
			if (dao.selectStikerOne(Integer.parseInt(checkSticker.get(i)))==1)
			{
				// 해당 i를 dto csticker_no에 넣어줌 
				dto.setCstickerNo(Integer.parseInt(checkSticker.get(i)));
				// 해당 스티커를 제거해줌
				dao.deleteSticker(dto);
			}
		}
    	
		// 북마크 지역정보 및 스티커 삭제
		dao.deleteLocalBookMarkSticker(lDto);
		dao.deleteLocalBookMark(dto);
    	
		// 북마크 삭제 후 해당 지역정보 조회하기 위한 dongNo
		int dongNo = Integer.parseInt(request.getParameter("dongNo"));
		
		// 해당 북마크 지역정보로 이동
		return "redirect:readlocal.action?dongNo=" + dongNo;
	}
	
}
