package com.seolo.idao;

import java.util.ArrayList;

import com.seolo.dto.BookmarkDTO;
import com.seolo.dto.ChecklistDTO;
import com.seolo.dto.LocalDTO;
import com.seolo.dto.PlusDTO;
import com.seolo.dto.ScoreDTO;

public interface IReadDAO
{
	public ChecklistDTO read(int checkNo);
	
	public ArrayList<ScoreDTO> scoreList();

	public BookmarkDTO isBookMark(BookmarkDTO dto);
	
	public BookmarkDTO isLocalBookMark(BookmarkDTO dto);
	
	public ArrayList<PlusDTO> listTime(int checkNo);
	
	public ArrayList<PlusDTO> listScore(int checkNo);
	
	public ArrayList<String> listBookmarkSticker(int chbNo);
	
	public ArrayList<String> listWriterSticker(int checkNo);
	
	public LocalDTO readLocal(String dongNo);
	
	public ArrayList<String> listLocalBookmarkSticker(int lobNo);

	// 북마크 체크리스트 수정
	public int updateCheckBookMark(BookmarkDTO dto);
	
	// 북마크 체크리스트 삭제
	public int deleteCheckBookMark(BookmarkDTO dto);
	
	// 북마크 지역정보 삭제
	public int deleteLocalBookMark(BookmarkDTO dto);
	
	// 북마크 지역정보 스티커 삭제
	public int deleteLocalBookMarkSticker(BookmarkDTO dto);
	
	// 북마크 체크리스트 스티커 삭제
	public int deleteCheckBookMarkSticker(BookmarkDTO dto);
	
	// 북마크 체크리스트 스티커 삭제용 CSTICKER_NO 선택
	public ArrayList<String> selectCheckStiker(int lobNo);
	
	// 북마크 지역정보 스티커 삭제용 CSTICKER_NO 선택
	public ArrayList<String> selectLocalStiker(int chbNo);
	
	// 북마크 스티커 테이블에서 스티커 삭제
	public int deleteSticker(BookmarkDTO dto);
	
	// 북마크 지역정보 스티커가 딱 하나인지 확인용
	public int selectStikerOne(int cstickerNo);
	
	// 북마크 체크리스트 스티커가 딱 하나인지 확인용
	public int selectStikerCheckOne(int cstickerNo);

}
