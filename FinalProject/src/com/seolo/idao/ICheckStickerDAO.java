/*==================================
   ICheckStickerDAO.java
   - 체크리스트 입력 중 세 번째
     스티커 입력에 관한 인터페이스
===================================*/

package com.seolo.idao;

import java.util.ArrayList;

import com.seolo.dto.StickerDTO;

public interface ICheckStickerDAO
{
	// 해당 계정이 가진 스티커 목록 가져오기
	public ArrayList<StickerDTO> stickerList(int acNo);
	
	// 이 계정이 가지고 있던 스티커를 해당 체크리스트에 붙이기(INSERT)
	public int putSticker(StickerDTO dto);
	
	// 지금 계정번호에서 가장 최근에 쓴 체크리스트 고유번호 가져오기
	public int searchCheckNo(int acNo);
	
	// 해당 체크리스트에 붙은 스티커 목록 가져오기
	public ArrayList<StickerDTO> thisCheck(int checkNo);
	
	// 이 계정의 스티커 목록에 새 스티커 추가하기(INSERT)
	public int addSticker(StickerDTO dto);
	
	// 지금 계정번호에서 가장 최근에 추가한 스티커 고유번호 가져오기
	public int searchStickerNo(int acNo);
}
