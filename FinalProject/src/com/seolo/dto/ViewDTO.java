package com.seolo.dto;

import java.util.ArrayList;

public class ViewDTO
{
	//○ ViewDTO 자료형 클래스
	//-- '나의 체크리스트' 게시판 조회 시 사용하는 자료형
	//   '체크리스트'와 '지역정보' 를 둘 다 조회할 수 있는 형태
	
	//○ 주요 속성 구성
	//-- 분류
	private String type; 				// 나의 체크리스트, 체크리스트 북마크, 지역정보 북마크
	private int markNo, listNo;			// PK: 북마크 번호, 실제 글 번호·동 번호
	
	//-- 세부 속성
	private String guName, dongName, title;
	private int deposit, wolse, jeonse, maemae, convenience;
	private double security, transport, honjap, pet;
	private ArrayList<StickerDTO> stickerList;	// 스티커 목록
	
	//○ getter, setter 구성
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public int getMarkNo()
	{
		return markNo;
	}
	public void setMarkNo(int markNo)
	{
		this.markNo = markNo;
	}
	public int getListNo()
	{
		return listNo;
	}
	public void setListNo(int listNo)
	{
		this.listNo = listNo;
	}
	public String getGuName()
	{
		return guName;
	}
	public void setGuName(String guName)
	{
		this.guName = guName;
	}
	public String getDongName()
	{
		return dongName;
	}
	public void setDongName(String dongName)
	{
		this.dongName = dongName;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public int getDeposit()
	{
		return deposit;
	}
	public void setDeposit(int deposit)
	{
		this.deposit = deposit;
	}
	public int getWolse()
	{
		return wolse;
	}
	public void setWolse(int wolse)
	{
		this.wolse = wolse;
	}
	public int getJeonse()
	{
		return jeonse;
	}
	public void setJeonse(int jeonse)
	{
		this.jeonse = jeonse;
	}
	public int getMaemae()
	{
		return maemae;
	}
	public void setMaemae(int maemae)
	{
		this.maemae = maemae;
	}
	public int getConvenience()
	{
		return convenience;
	}
	public void setConvenience(int convenience)
	{
		this.convenience = convenience;
	}
	public double getSecurity()
	{
		return security;
	}
	public void setSecurity(double security)
	{
		this.security = security;
	}
	public double getTransport()
	{
		return transport;
	}
	public void setTransport(double transport)
	{
		this.transport = transport;
	}
	public double getHonjap()
	{
		return honjap;
	}
	public void setHonjap(double honjap)
	{
		this.honjap = honjap;
	}
	public double getPet()
	{
		return pet;
	}
	public void setPet(double pet)
	{
		this.pet = pet;
	}
	public ArrayList<StickerDTO> getStickerList()
	{
		return stickerList;
	}
	public void setStickerList(ArrayList<StickerDTO> stickerList)
	{
		this.stickerList = stickerList;
	}

}
