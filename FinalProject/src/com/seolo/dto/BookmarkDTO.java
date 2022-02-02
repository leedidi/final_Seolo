package com.seolo.dto;

public class BookmarkDTO
{
	private int chbNo, lobNo, checkNo;
	// chbNo : 체크리스트 북마크 번호, lobNo : 지역 북마크 번호
	private String acNo, title, markDate, dongNo;
	
	private int chtagNo, lotagNo, cstickerNo;
	// 부착스티커 테이블들의 고유번호들과 스티커고유번호

	public BookmarkDTO()
	{
	
	}
	
	public BookmarkDTO(String acNo, int checkNo)
	{
		this.acNo = acNo;
		this.checkNo = checkNo;
	}
	
	public BookmarkDTO(String acNo, String dongNo)
	{
		this.acNo = acNo;
		this.dongNo = dongNo;
	}
	
	public int getChbNo()
	{
		return chbNo;
	}
	public void setChbNo(int chbNo)
	{
		this.chbNo = chbNo;
	}
	public int getLobNo()
	{
		return lobNo;
	}
	public void setLobNo(int lobNo)
	{
		this.lobNo = lobNo;
	}
	public int getCheckNo()
	{
		return checkNo;
	}
	public void setCheckNo(int checkNo)
	{
		this.checkNo = checkNo;
	}
	public String getAcNo()
	{
		return acNo;
	}
	public void setAcNo(String acNo)
	{
		this.acNo = acNo;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getMarkDate()
	{
		return markDate;
	}
	public void setMarkDate(String markDate)
	{
		this.markDate = markDate;
	}
	public String getDongNo()
	{
		return dongNo;
	}
	public void setDongNo(String dongNo)
	{
		this.dongNo = dongNo;
	}

	public int getChtagNo()
	{
		return chtagNo;
	}

	public void setChtagNo(int chtagNo)
	{
		this.chtagNo = chtagNo;
	}

	public int getLotagNo()
	{
		return lotagNo;
	}

	public void setLotagNo(int lotagNo)
	{
		this.lotagNo = lotagNo;
	}

	public int getCstickerNo()
	{
		return cstickerNo;
	}

	public void setCstickerNo(int cstickerNo)
	{
		this.cstickerNo = cstickerNo;
	}
	

}
