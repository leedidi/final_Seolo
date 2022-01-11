package com.seolo.idao;

import com.seolo.dto.FaqRunDTO;

public interface IFaqRunDAO
{

	// 게시물 생성
	public int add(FaqRunDTO n);
	
	// 게시물 수정 
	public int modify(FaqRunDTO n);
	
	// 게시물 삭제
	public int remove(FaqRunDTO n);
	
}


