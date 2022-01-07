package com.seolo.idao;

import java.util.ArrayList;

import com.seolo.dto.DongDTO;
import com.seolo.dto.GuDTO;
import com.seolo.dto.StickerDTO;
import com.seolo.dto.ViewDTO;
import com.seolo.dto.ViewMakerDTO;

public interface IViewDAO
{
	//○ '나의 체크리스트' FORM 조회 시 사용하는 메소드
	public ArrayList<GuDTO> guList();
	public ArrayList<DongDTO> dongList();
	public ArrayList<DongDTO> dongListGuNo(int guNo);
	public ArrayList<StickerDTO> stickerList(int acNo);
	
	//○ '나의 체크리스트' 조회 시 사용하는 메소드
	//-- 선택 분기에 따라 분기 : ①구/동 둘 다 선택, ②구만 선택, ③둘 다 선택 안 함
	//-- ※계정과 TYPE 는 언제든지 선택값이 들어감
	//-- ※단, 위 분기는 '동'선택 시 반드시 '구'가 선택되어 있을 때를 전제함(ajax 혹은 javascript로 구현) 
	public ArrayList<ViewDTO> listAllCheck(ViewMakerDTO dto);
	public ArrayList<ViewDTO> listGuCheck(ViewMakerDTO dto);
	public ArrayList<ViewDTO> listNoneCheck(ViewMakerDTO dto);
	
	//○ 각 VIEW 의 스티커 조회 시 사용하는 메소드
	//-- VIEW 의 유형에 따라 분기 : ①나의 체크리스트, ②북마크 체크리스트, ③북마크 지역정보
	public ArrayList<StickerDTO> mycheckStickerList(int no);
	public ArrayList<StickerDTO> checkBookmarkStickerList(int no);
	public ArrayList<StickerDTO> localBookmarkStickerList(int no);
	
}
