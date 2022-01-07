package com.seolo.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.seolo.dto.DongDTO;
import com.seolo.dto.GuDTO;
import com.seolo.dto.StickerDTO;
import com.seolo.dto.ViewDTO;
import com.seolo.dto.ViewMakerDTO;
import com.seolo.idao.IViewDAO;
import com.seolo.personal.PersonalDTO;

@Controller
public class MyChecklistController
{
	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/mychecklistform.action", method = RequestMethod.GET)
	public String myCheckListForm(Model model, HttpSession session)
	{
		IViewDAO dao = sqlSession.getMapper(IViewDAO.class);

		//○ 세션 확인: 로그인 상태 확인 → 정보가 없을 경우 main 으로
		if (session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		//○ 계정 정보 받아오기
		PersonalDTO user = (PersonalDTO) session.getAttribute("userLogin");
		int acNo = Integer.parseInt(user.getAc_No());

		//○ 페이지 구현과 관련된 작업 처리
		ArrayList<GuDTO> guList = new ArrayList<GuDTO>();
		ArrayList<DongDTO> dongList = new ArrayList<DongDTO>();
		ArrayList<StickerDTO> stickerList = new ArrayList<StickerDTO>();

		try
		{
			guList = dao.guList();
			dongList = dao.dongList();
			stickerList = dao.stickerList(acNo);

			model.addAttribute("guList", guList);
			model.addAttribute("dongList", dongList);
			model.addAttribute("stickerList", stickerList);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return "WEB-INF/view/MyChecklistsForm.jsp";

	}

	@RequestMapping(value = "/mychecklist.action", method = RequestMethod.POST)
	public String myCheckList(Model model, HttpSession session, String type, int guNo, int dongNo, String[] bigyo,
			String[] sticker)
	{
		IViewDAO dao = sqlSession.getMapper(IViewDAO.class);

		//○ 세션 확인: 로그인 상태 확인 → 정보가 없을 경우 main 으로
		if (session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		//○ 계정 정보 받아오기
		PersonalDTO user = (PersonalDTO) session.getAttribute("userLogin");
		int acNo = Integer.parseInt(user.getAc_No());

		
		//○ 페이지 구현과 관련된 작업 처리
		// ① 선택된 알아볼 내용 값 받아오기
		ViewMakerDTO viewMaker = new ViewMakerDTO();
		viewMaker.setAcNo(acNo);		//-- 계정 번호
		viewMaker.setType(type);		//-- 타입: %, 나의 체크리스트, 북마크 체크리스트, 북마크 지역정보
		viewMaker.setGuNo(guNo);		//-- 구 번호
		viewMaker.setDongNo(dongNo);	//-- 동 번호
		
		// ② String[] bigyo: 선택된 비교 값 받아오기
		boolean wolse = Arrays.asList(bigyo).contains("wolse");
		boolean jeonse = Arrays.asList(bigyo).contains("jeonse");
		boolean maemae = Arrays.asList(bigyo).contains("maemae");
		boolean security = Arrays.asList(bigyo).contains("security");
		boolean transportation = Arrays.asList(bigyo).contains("transportation");
		boolean honjap = Arrays.asList(bigyo).contains("honjap");
		boolean conv = Arrays.asList(bigyo).contains("conv");
		boolean pet = Arrays.asList(bigyo).contains("pet");

		// ③ String[] sticker: 선택된 스티커 목록 → 스티커 번호

		// ④ 조회할 뷰 목록 생성
		ArrayList<ViewDTO> viewListTemp = new ArrayList<ViewDTO>();	//-- 중복값 제거 전 View
		ArrayList<ViewDTO> viewList = new ArrayList<ViewDTO>();		//-- 중복값 제거된 View

		try
		{
			// 4-1. 뷰 목록에 값 넣기 : 구·동 선택 여부에 따라 분기
			if (guNo != 0 && dongNo != 0) 	// 구·동 둘 다 선택 O
			{
				for (int i = 0; i < sticker.length; i++)
				{
					viewMaker.setStickerNo(Integer.parseInt(sticker[i]));	//-- 스티커 번호
					viewListTemp.addAll(dao.listAllCheck(viewMaker));
				}
			}
			else if (guNo != 0) 			// 구만 선택
			{
				for (int i = 0; i < sticker.length; i++)
				{
					viewMaker.setStickerNo(Integer.parseInt(sticker[i]));	//-- 스티커 번호
					viewListTemp.addAll(dao.listGuCheck(viewMaker));
				}
			}
			else 							// 구·동 둘 다 선택 X
			{
				for (int i = 0; i < sticker.length; i++)
				{
					viewMaker.setStickerNo(Integer.parseInt(sticker[i]));	//-- 스티커 번호
					viewListTemp.addAll(dao.listNoneCheck(viewMaker));
				}
			}

			
			// 4-2. 뷰 목록에 값이 있다면....
			if (viewListTemp.size() != 0)
			{
				// 4-2. 중복값 제거 
				viewList.add(viewListTemp.get(0));

				for (int i = 1; i < viewListTemp.size(); i++)		//-- 확인하는 쪽
				{
					boolean flag = false; 							//-- 중복 O: true, 중복 X: false

					for (int y = 0; y < viewList.size(); y++)		//-- 확인 당하는 쪽
					{
						if (viewListTemp.get(i).getType().equals(viewList.get(y).getType())			//-- 타입이 같고
								&& viewListTemp.get(i).getMarkNo() == viewList.get(y).getMarkNo())	//-- 번호가 같은 경우
						{
							flag = true;
							break;
						}
					}

					if (!flag)	//-- 중복이 아닐 경우 저장
						viewList.add(viewListTemp.get(i));
				}
				

				// ⑤ 각 뷰의 스티커 목록 생성
				//　　: 각 뷰의 TYPE에 따라 분기 -- 나의 체크리스트, 북마크 체크리스트, 지역정보 체크리스트
				for (ViewDTO dto : viewList)
				{
					String viewType = dto.getType();
					int no = dto.getMarkNo();

					if (viewType.equals("나의 체크리스트"))
						dto.setStickerList(dao.mycheckStickerList(no));
					else if (viewType.equals("북마크 체크리스트"))
						dto.setStickerList(dao.checkBookmarkStickerList(no));
					else
						dto.setStickerList(dao.localBookmarkStickerList(no));
				}

			}
			

			// ○ 결정된 값 보내기
			model.addAttribute("wolse", wolse);
			model.addAttribute("jeonse", jeonse);
			model.addAttribute("maemae", maemae);
			model.addAttribute("security", security);
			model.addAttribute("transportation", transportation);
			model.addAttribute("honjap", honjap);
			model.addAttribute("conv", conv);
			model.addAttribute("pet", pet);

			model.addAttribute("viewList", viewList);

		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return "WEB-INF/view/MyChecklists.jsp";
	}

	@RequestMapping(value = "/dongnameajax.action", method = RequestMethod.GET)
	public String dongNameAjax(Model model, HttpSession session)
	{
		//IViewDAO dao = sqlSession.getMapper(IViewDAO.class);

		// 세션 적용: 로그인 정보가 없을 경우 main 으로
		if (session.getAttribute("userLogin") == null)
			return "redirect:main.action";

		// Ajax 처리

		return "WEB-INF/view/ChecklistWrite_first.jsp";
	}

}
