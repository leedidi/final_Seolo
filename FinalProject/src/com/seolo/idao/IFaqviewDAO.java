package com.seolo.idao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.seolo.dto.FaqviewDTO;

public interface IFaqviewDAO
{
	// 자주 묻는 질문 게시글 조회
	public ArrayList<FaqviewDTO> list(@Param("start") int start, @Param("end") int end);
	
	// 자주 묻는 질문 게시글 조회 : 카테고리 선택 시
	public ArrayList<FaqviewDTO> cateList(@Param("start") int start, @Param("end") int end, @Param("qs_no") String qs_no);

	// 자주 묻는 질문 게시글(노드) 조회
	public FaqviewDTO view(int num);

	// 자주 묻는 질문 카테고리 조회
	public ArrayList<FaqviewDTO> cateNameList();

	// 자주 묻는 질문 게시판 - 카테고리별 조회
	public ArrayList<FaqviewDTO> cateList(String faq_check);

	// 전체 게시글 갯수 확인
	public int count();
	
	// 전체 게시글 갯수 확인 : 카테고리 확인 시 
	public int cateCount(String qs_no);

}
