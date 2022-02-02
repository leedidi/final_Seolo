package com.seolo.idao;

import java.util.ArrayList;

import com.seolo.dto.ReportviewDTO;
import com.seolo.personal.PersonalDTO;

public interface IUpdateDAO
{
	public PersonalDTO searchId(String pe_Id);	// 마이페이지 정보
	public int update(PersonalDTO dto);			// 회원정보 수정
	
	public int confirmPwd(PersonalDTO dto);		// 비밀번호 바꾸기 전 비밀번호 확인
	public int updatePwd(PersonalDTO dto);		// 비밀번호 변경

	public ArrayList<ReportviewDTO> myinfoList(String reportername);	// 마이페이지 - 나의 신고리스트(최신순 3개) 조회
	
	public int reportApproval(String pe_Id);				// 마이페이지 - 신고당한 후 승인처리 된 횟수
	public int reportFake(String pe_Id);					// 마이페이지 - 허위신고한 횟수
	
	public int imgUpdate(PersonalDTO dto); // 이미지 업로드
	public String searchPro(String pe_id); // 이미지 가져오기
	
}
