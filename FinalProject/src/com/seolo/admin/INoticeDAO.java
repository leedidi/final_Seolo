/*======================
   INoticeDAO.java
   - 인터페이스
=======================*/

package com.seolo.admin;

import java.util.ArrayList;

import com.seolo.dto.PageDTO;

public interface INoticeDAO
{	
	// 게시물 목록
	public ArrayList<NoticeDTO> list(PageDTO dto);
		
	// 게시물 카테고리 목록
	public ArrayList<NoticeDTO> cateNamelist();
	
	// 게시물 작성
	public int add(NoticeDTO n);
	
	// 게시물 조회
	public NoticeDTO view(int num);

	// 게시물 수정 
	public int modify(NoticeDTO n);
	
	// 게시물 삭제
	public int remove(NoticeDTO n);
	
	// 게시물 총 개수
	public int count();
	
	// 게시물 목록 - 카테고리별 조회
	public ArrayList<NoticeDTO> catelist(PageDTO dto);
	
	// 변수가 여러개(2개 이상) 일 경우 오류 뜰 수 있음, 아래처럼 @Param 붙여주면 해결
	//public ArrayList<NoticeDTO> catelist(PageDTO dto, @Param("start") int start, @Param("end") int end, @Param("notice_check") String notice_check);

	// 게시물 카테고리별 총 개수
	public int cateCount(String nos_no);
}
