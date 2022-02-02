package com.seolo.idao;

import com.seolo.dto.ReportRunDTO;

public interface IReportRunDAO
{
	// 신고 승인 처리
	public int approval(ReportRunDTO dto);
	
	// 신고 반려 처리
	public int reject(ReportRunDTO dto);
	
	// 허위신고 처리
	public int fake(ReportRunDTO dto);
	
	// 마이페이지 - 나의 신고리스트 취소
	public int delete(ReportRunDTO dto);
}
