package com.seolo.dto;

public class ReportRunDTO
{
	// status_no	신고 상태
	// rpcheck_no	신고 고유번호
	// ad_id		관리자 고유번호
	// confirmdate	날짜
	
	String status_no, rpcheck_no, ad_id, confirmdate;

	// getter / setter 구성
	public String getStatus_no()
	{
		return status_no;
	}

	public void setStatus_no(String status_no)
	{
		this.status_no = status_no;
	}

	public String getRpcheck_no()
	{
		return rpcheck_no;
	}

	public void setRpcheck_no(String rpcheck_no)
	{
		this.rpcheck_no = rpcheck_no;
	}
	public String getAd_id()
	{
		return ad_id;
	}

	public void setAd_id(String ad_id)
	{
		this.ad_id = ad_id;
	}
	public String getConfirmdate()
	{
		return confirmdate;
	}

	public void setConfirmdate(String confirmdate)
	{
		this.confirmdate = confirmdate;
	}
	
}

