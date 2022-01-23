package com.seolo.idao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.seolo.dto.ReportviewDTO;

public interface IReportviewDAO
{
	// 신고 게시판: 전체 조회
	public ArrayList<ReportviewDTO> list(@Param("start") int start, @Param("end") int end);

	// 신고 게시판: 카테고리별 조회
	public ArrayList<ReportviewDTO> cateList(@Param("start") int start, @Param("end") int end, @Param("report_check") String report_check);

	// 신고 게시판: 전체 게시글 갯수
	public int count();
	
	// 신고 게시판: 카테고리별 게시글 갯수
	public int cateCount(String report_check);
	
	// 신고 게시글 조회
	public ReportviewDTO view(int num);

	// 마이페이지: 나의 신고리스트(전체) 조회
	public ArrayList<ReportviewDTO> myinfoAllList(@Param("start") int start, @Param("end") int end, @Param("reportername") String reportername);
	
	// 마이페이지: 각 유저의 신고게시글 갯수 조회
	public int myCount(String reportername);		

	// 신고 게시판 - 카테고리 조회
	public ArrayList<ReportviewDTO> cateNameList();

}
