package com.seolo.idao;

import java.util.ArrayList;

import com.seolo.dto.ReportRunDTO;
import com.seolo.dto.ReportviewDTO;

public interface IReportviewDAO
{
	// 신고 게시판 조회
	public ArrayList<ReportviewDTO> list();
	
	// 신고 게시글 조회
	public ReportviewDTO view(int num);
	
	// 마이페이지 - 나의 신고리스트(최신순 3개) 조회
	//public ArrayList<ReportviewDTO> myinfoList(String reportername);
	//public ArrayList<ReportviewDTO> myinfoList();

	// 마이페이지 - 나의 신고리스트(전체) 조회
	public ArrayList<ReportviewDTO> myinfoAllList(String reportername);

	// 신고 게시판 - 카테고리별 조회
	public ArrayList<ReportviewDTO> cateList(String report_check);
	
	// 신고 게시판 - 카테고리 조회
	public ArrayList<ReportviewDTO> cateNameList();
}
