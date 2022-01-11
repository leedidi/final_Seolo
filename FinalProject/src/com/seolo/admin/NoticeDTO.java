/*====================
	NoticeDTO.java
	- 공지사항 DTO
=====================*/

package com.seolo.admin;

//NOTICE(NO_NO, AD_ID, NOS_NO, TITLE, CONTENT, NODATE) + NOS_NAME

// no_no		공지사항 고유번호
// ad_id		관리자 아이디
// nos_no		공지사항 분류 고유번호
// nos_name 	공지사항 분류명
// title		제목
// content		내용
// nodate		작성일시	
// name     	공지사항 분류명 - 카테고리 출력용
// notice_check	공지사항 분류명 - 카테고리 선택용

public class NoticeDTO
{
	private int no_no, nos_no;
	private String ad_id, nos_name, title, content;
	private String nodate;
	private String name;
	private String notice_check;
	
	// getter/setter 구성
	public int getNo_no()
	{
		return no_no;
	}
	public void setNo_no(int no_no)
	{
		this.no_no = no_no;
	}
	public int getNos_no()
	{
		return nos_no;
	}
	public void setNos_no(int nos_no)
	{
		this.nos_no = nos_no;
	}
	public String getAd_id()
	{
		return ad_id;
	}
	public void setAd_id(String ad_id)
	{
		this.ad_id = ad_id;
	}
	public String getNos_name()
	{
		return nos_name;
	}
	public void setNos_name(String nos_name)
	{
		this.nos_name = nos_name;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getNodate()
	{
		return nodate;
	}
	public void setNodate(String nodate)
	{
		this.nodate = nodate;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getNotice_check()
	{
		return notice_check;
	}
	public void setNotice_check(String notice_check)
	{
		this.notice_check = notice_check;
	}
	
}
