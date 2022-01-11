package com.seolo.idao;

import java.util.ArrayList;

import com.seolo.dto.FaqviewDTO;

public interface IFaqviewDAO
{
	// 자주 묻는 질문 게시판 조회
	public ArrayList<FaqviewDTO> list();
	
	// 자주 묻는 질문 게시글(노드) 조회
	public FaqviewDTO view(int num);
	
	// 자주 묻는 질문 카테고리 조회
	public ArrayList<FaqviewDTO> cateNameList();
	
	// 자주 묻는 질문 게시판 - 카테고리별 조회
	public ArrayList<FaqviewDTO> cateList(String faq_check);
	
}	
