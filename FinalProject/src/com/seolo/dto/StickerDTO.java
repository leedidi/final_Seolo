package com.seolo.dto;

public class StickerDTO
{
	private int stickerNo, checkNo, acNo;		// checkNo, acNo 입력 3번 페이지 때문에 추가
	private String content;

	public int getStickerNo()
	{
		return stickerNo;
	}
	public void setStickerNo(int stickerNo)
	{
		this.stickerNo = stickerNo;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public int getCheckNo()
	{
		return checkNo;
	}
	public void setCheckNo(int checkNo)
	{
		this.checkNo = checkNo;
	}
	public int getAcNo()
	{
		return acNo;
	}
	public void setAcNo(int acNo)
	{
		this.acNo = acNo;
	}
	

}
