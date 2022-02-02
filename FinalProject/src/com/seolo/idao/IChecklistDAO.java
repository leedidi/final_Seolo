/*==================================
   IChecklistDAO.java
   - 체크리스트 입력 중 첫 번째
     기본항목 입력에 관한 인터페이스
===================================*/

package com.seolo.idao;

import java.util.ArrayList;

import com.seolo.dto.ChecklistDTO;
import com.seolo.dto.FaqRunDTO;
import com.seolo.dto.ScoreDTO;

public interface IChecklistDAO
{
	/* 매개변수를 다 DTO로 변경해줌. */
	
	// 체크리스트 입력할 때 들어가는 것.
	// 체크 고유번호, 계정 고유번호, 거주여부 고유번호, 동 고유번호, 작성일시, 제목, 도로명주소
	public int add(ChecklistDTO dto);

	// 월세는 월세와 보증금이 같이 입력. 고유번호 통해서
	public int addWolse(ChecklistDTO dto);
	public int addJeonse(ChecklistDTO dto);
	public int addMaemae(ChecklistDTO dto);

	// 반려동물은 입력할거면 점수는 필수, 코멘트는 선택(이건 스크립트에서 제어)
	public int addPet(ChecklistDTO dto);
	public int addPetco(ChecklistDTO dto);

	public int addSecurity(ChecklistDTO dto);
	public int addSecurityco(ChecklistDTO dto);
	
	public int addTransport(ChecklistDTO dto);
	public int addTransportco(ChecklistDTO dto);
	
	public int addHonjap(ChecklistDTO dto);
	public int addHonjapco(ChecklistDTO dto);
	
	public int addContentG(ChecklistDTO dto);
	public int addContentB(ChecklistDTO dto);
	public int addContentE(ChecklistDTO dto);

	public int addSecret(ChecklistDTO dto);

	public int addConvenience(ChecklistDTO dto);
	public int addConvenienceco(ChecklistDTO dto);

	// 동 이름으로 동 번호 알아내기
	public int searchDong(String dongName);
	
	// 동 번호로 동 이름 얻어내기
	public String searchDongName(String dongNo);

	// 동코드를 통해서 값이 있으면 1, 없으면 0 count 메소드
	public int checkdong(String dongNo);

	public ArrayList<ScoreDTO> scoreList();

	public ArrayList<String> residenceList();
	
	// 지금 계정번호에서 가장 최근에 쓴 체크리스트 번호 가져오기 (sysdate로 정렬해서 첫번째꺼)
	public int searchCheckNo(int acNo);
	
	// 체크리스트 삭제*************************************************
	
	public int deletechecklist1(String checkNo);
	public int deletechecklist2(String checkNo);
	public int deletechecklist3(String checkNo);
	public int deletechecklist4(String checkNo);
	public int deletechecklist5(String checkNo);
	public int deletechecklist6(String checkNo);
	public int deletechecklist7(String checkNo);
	public int deletechecklist8(String checkNo);
	public int deletehonjap(String checkNo);
	public int deletepet(String checkNo);
	public int deletecon(String checkNo);
	public int deletechecklist9(String checkNo);
	
	/*
	// 시간추가항목 전체리스트 가져오기 - 고유번호가 큰 순 정렬
	public ArrayList<PlusDTO> timeList(int checkNo);
	
	// 점수추가항목 전체리스트 가져오기 - 고유번호가 큰 순 정렬
	public ArrayList<PlusDTO> scoreList(int checkNo);
	*/
	
	/*
	// 체크리스트 입력할 때 들어가는 것.
	// 체크 고유번호, 계정 고유번호, 거주여부 고유번호, 동 고유번호, 작성일시, 제목, 도로명주소
	public int add(int checkNo, int acNo, int res_no, int dongNo, String checkDate, String title, String roadAddr);

	// 월세는 월세와 보증금이 같이 입력. 고유번호 통해서
	public int addWolse(int checkNo, int mWolse, int deposit);
	public int addJeonse(int checkNo, int mJeonse);
	public int addMaemae(int checkNo, int mMaemae);

	// 반려동물은 입력할거면 점수는 필수, 코멘트는 선택(이건 스크립트에서 제어)
	public int addPet(int checkNo, int pet_scoreNo);
	public int addPetco(int checkNo, String pet_co);

	public int addSecurity(int checkNo, int security_scoreNo);
	public int addSecurityco(int checkNo, String security_co);
	
	public int addTransport(int CheckNo, int transport_scoreNo);
	public int addTransportco(int checkNo, String transport_co);
	
	public int addHonjap(int checkNo, int honjap_scoreNo);
	public int addHonjapco(int checkNo, String honjap_co);
	
	public int addContentG(int checkNo, String good);
	public int addContentB(int checkNo, String bad);
	public int addContentE(int checkNo, String etc);

	public int addSecret(int checkNo, String secret_co);

	public int addConvenience(int checkNo, int mart, int laundry, int hospital, int food, int culture, int park);
	public int addConvenienceco(int checkNo, String conv_co);

	// 동 이름으로 동 번호 알아내기
	public int searchDong(String dongName);
	
	// 동 번호로 동 이름 얻어내기
	public String searchDongName(String dongNo);

	// 동코드를 통해서 값이 있으면 1, 없으면 0 count 메소드
	public int checkdong(String dongNo);

	public ArrayList<ScoreDTO> scoreList();

	public ArrayList<String> residenceList();
	*/
	
	// 추가항목은 IPlusDAO에서.
	// 스티커는 IStickerDAO에서.
	
	
}