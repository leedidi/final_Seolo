package com.seolo.dto;

public class ViewMakerDTO
{
	//○ ViewList를 만들 때 사용하는 함수에서 사용할 매개변수
	//　 (ViewDAO 의 listAllCheck, listGuCheck, listNoneCheck) 
	//　 -- 계정번호, 구번호, 동번호, 스티커번호
	//　 -- 타입: %(전체 보기), 나의 체크리스트, 북마크 체크리스트, 북마크 지역정보
	private int acNo, guNo, dongNo, stickerNo;
	private String type;
	
	//○ getter, setter
	public int getAcNo()
	{
		return acNo;
	}
	public void setAcNo(int acNo)
	{
		this.acNo = acNo;
	}
	public int getGuNo()
	{
		return guNo;
	}
	public void setGuNo(int guNo)
	{
		this.guNo = guNo;
	}
	public int getDongNo()
	{
		return dongNo;
	}
	public void setDongNo(int dongNo)
	{
		this.dongNo = dongNo;
	}
	public int getStickerNo()
	{
		return stickerNo;
	}
	public void setStickerNo(int stickerNo)
	{
		this.stickerNo = stickerNo;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
}
