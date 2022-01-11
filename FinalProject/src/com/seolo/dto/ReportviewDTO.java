package com.seolo.dto;

import java.sql.Date;

public class ReportviewDTO
{
	/* 
	   RNUM 		행 번호 
	   TITLE 		제목(신고 유형)
	   STATUS_NO	신고 번호
	   STATUSNAME 	상태(신고 상태 - 승인/반려/허위신고/미해결)
	   REPORTDATE	신고 날짜
	   REPORTERNO	신고한 사람 고유번호
	   REPORTERNAME	신고한 사람 아이디 
	   WRITERNO		신고당한 사람(체크리스트 작성자) 고유번호
	   WRITERNAME	신고당한 사람(체크리스트 작성자) 아이디 
	   RPCHECK_NO	신고 고유번호
	   CHECK_NO		신고당한 체크리스트 고유번호
	   WHY			상세사유 
	   REPORT_CHECK	신고 상태 카테고리
	 */
	
	String rnum, title, statusname, reporterno, reportername, writerno, writername, rpcheck_no, why, check_no, report_check;
	Date reportdate;
	int status_no;
	
	// getter / setter
	public String getRnum()
	{
		return rnum;
	}
	public void setRnum(String rnum)
	{
		this.rnum = rnum;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getStatusname()
	{
		return statusname;
	}
	public void setStatusname(String statusname)
	{
		this.statusname = statusname;
	}
	public String getReporterno()
	{
		return reporterno;
	}
	public void setReporterno(String reporterno)
	{
		this.reporterno = reporterno;
	}
	public String getReportername()
	{
		return reportername;
	}
	public void setReportername(String reportername)
	{
		this.reportername = reportername;
	}
	public String getWriterno()
	{
		return writerno;
	}
	public void setWriterno(String writerno)
	{
		this.writerno = writerno;
	}
	public String getWritername()
	{
		return writername;
	}
	public void setWritername(String writername)
	{
		this.writername = writername;
	}
	public String getRpcheck_no()
	{
		return rpcheck_no;
	}
	public void setRpcheck_no(String rpcheck_no)
	{
		this.rpcheck_no = rpcheck_no;
	}
	public String getWhy()
	{
		return why;
	}
	public void setWhy(String why)
	{
		this.why = why;
	}
	public Date getReportdate()
	{
		return reportdate;
	}
	public void setReportdate(Date reportdate)
	{
		this.reportdate = reportdate;
	}
	public String getCheck_no()
	{
		return check_no;
	}
	public void setCheck_no(String check_no)
	{
		this.check_no = check_no;
	}
	public String getReport_check()
	{
		return report_check;
	}
	public void setReport_check(String report_check)
	{
		this.report_check = report_check;
	}
	public int getStatus_no()
	{
		return status_no;
	}
	public void setStatus_no(int status_no)
	{
		this.status_no = status_no;
	}
	
	
	
	
}
