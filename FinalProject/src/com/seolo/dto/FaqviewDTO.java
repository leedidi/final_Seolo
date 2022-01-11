package com.seolo.dto;

public class FaqviewDTO
{
	/*  FAQ_NO 			자주 묻는 질문 고유번호
	    FAQSORT_NO		자주 묻는 질문 카테고리 
	    FAQSORT_NAME	자주 묻는 질문 카테고리 이름
	    FAQ_TITLE		제목
	    FAQ_CONTENT 	내용
	    FAQ_CHECK		자주 묻는 질문 선택 카테고리
	    QS_NO			카테고리 번호
	    NAME			카테고리 이름
	*/
	
	String faq_no, faqsort_no, faqsort_name, faq_title, faq_content, faq_check, qs_no, name;

	// getter / setter 구성
	public String getFaq_no()
	{
		return faq_no;
	}

	public void setFaq_no(String faq_no)
	{
		this.faq_no = faq_no;
	}

	public String getFaqsort_no()
	{
		return faqsort_no;
	}

	public void setFaqsort_no(String faqsort_no)
	{
		this.faqsort_no = faqsort_no;
	}

	public String getFaqsort_name()
	{
		return faqsort_name;
	}

	public void setFaqsort_name(String faqsort_name)
	{
		this.faqsort_name = faqsort_name;
	}

	public String getFaq_title()
	{
		return faq_title;
	}

	public void setFaq_title(String faq_title)
	{
		this.faq_title = faq_title;
	}

	public String getFaq_content()
	{
		return faq_content;
	}

	public void setFaq_content(String faq_content)
	{
		this.faq_content = faq_content;
	}

	public String getFaq_check()
	{
		return faq_check;
	}

	public void setFaq_check(String faq_check)
	{
		this.faq_check = faq_check;
	}

	public String getQs_no()
	{
		return qs_no;
	}

	public void setQs_no(String qs_no)
	{
		this.qs_no = qs_no;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	
	
}
