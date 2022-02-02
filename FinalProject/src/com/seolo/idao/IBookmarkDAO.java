/*=========================
   IBookmarkDAO.java
   - 북마크 인터페이스
==========================*/

package com.seolo.idao;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.seolo.dto.BookmarkDTO;
import com.seolo.dto.StickerDTO;


public interface IBookmarkDAO
{
	// 내 스티커 리스트
	public ArrayList<String> stickerList(int acNo);
	
	// 내 스티커에 추가하기
	public int addSticker(StickerDTO dto);
	
	// 체크리스트 북마크 인서트
	public int addCheckBookmark(BookmarkDTO dto);
	
	// 체크리스트 부착스티커(태그) 인서트
	public int addCheckSticker(BookmarkDTO dto);
	
	// 지역정보 북마크 인서트
	public int addLocalBookmark(BookmarkDTO dto);
	
	// 지역정보 부착스티커 인서트
	public int addLocalSticker(BookmarkDTO dto);
	
	// 이 계정에서 가장 최근에 인서트된 지역정보 북마크 고유번호
	public Integer searchLocalBookNo(int acNo);
	
	// 이 계정에서 가장 최근에 인서트된 체크리스트 북마크 고유번호
	public int searchCheckBookNo(int acNo);
	
	// 이 계정에서 가장 최근에 인서트된 스티커 DTO
	public StickerDTO searchSticker(int acNo);
	
	// 해당 지역북마크에 붙은 스티커 목록
	public ArrayList<StickerDTO> localStickerList(@Param("acNo") int acNo, @Param("dongNo") String dongNo);
	
	// 해당 체크북마크에 붙은 스티커 목록
	public ArrayList<StickerDTO> checkStickerList(@Param("acNo") int acNo, @Param("checkNo") int checkNo);
	
	// 지역북마크가 되어있는지 아닌지를 판별/ 북마크 고유번호(LO_B_NO) 가져온다.
	public String localbookmarkornot(BookmarkDTO dto);
	
	// 지역부착스티커 삭제하기
	public int removeLocaltag(BookmarkDTO dto);
	
	// 지역북마크 삭제하기
	public int removeLocalBookmark(BookmarkDTO dto);		
	
	// 체크북마크가 되어있는지 아닌지를 판별/ 북마크 고유번호(CH_B_NO) 가져온다
	// public BookmarkDTO checkbookmarkornot(@Param("acNo") int acNo, @Param("dongNo") String dongNo);
	public String checkbookmarkornot(BookmarkDTO dto);
	
	// 체크부착스티커 삭제하기
	public int removeChecktag(BookmarkDTO dto);
	
	// 체크북마크 삭제하기
	public int removeCheckBookmark(BookmarkDTO dto);
	
}
