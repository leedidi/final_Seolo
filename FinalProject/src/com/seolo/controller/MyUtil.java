package com.seolo.controller;

public class MyUtil
{
	// ■ 전체 페이지 수를 구하는 메소드
	// numPerPage : 한 페이지에 표시할 데이터(게시물)의 수
	// dataCount  : 전체 데이터(게시물) 수
	public int getPageCount(int numPerPage, int dataCount)
	{
		int pageCount = 0;

		pageCount = dataCount / numPerPage;

		if(dataCount % numPerPage != 0)
			pageCount++;

		return pageCount;
	}
	//-- 한 페이지에 10 개의 게시물을 출력할 때
	//   총 32 개의 게시물을 페이지로 구성하기 위해서는
	//	 『32 / 10 』 의 연산을 수행하여 결과 3을 얻을 수 있다.
	//  -> 『pageCount = dataCount/numPerPage;』
	//	그런데 이 때, 나머지 2개의 게시물을 출력해 주기 위해서는
	//	페이지 하나가 더 필요하다.

	
	// ■ 페이징 처리 기능의 메소드
	// currentPage	: 현재 표시할 페이지
	// totalPage 	: 전체 페이지 수
	// listUrl 		: 링크를 설정할 url
	public String pageIndexList(int currentPage, int totalPage, String listUrl)
	{
		//○ 주요 변수 선언
		StringBuffer strList = new StringBuffer();
		//-- 실제 페이징을 저장할 StringBuffer 변수
		int numPerBlock = 10;
		//-- 페이징 처리 시 게시물 리스트 하단의 숫자를 10 개씩 보여주겠다.
		int currentPageSetup;
		//-- 현재 페이지(이 페이지를 기준으로 보여주는 숫자가 달라져야 하기 때문...)
		int page;
		//-- 이전 페이지 블럭과 같은 처리에서 이동하기 위한 변수
		int n;
		//-- 페이지 계산 시 사용할 변수

		
		//○ 페이징 처리가 별도로 필요하지 않은 경우
		//-- 데이터가 존재하지 않거나 데이터의 수가
		//   1 페이지도 못 채우는 경우는 별도로 페이징 처리를 할 필요가 없다.
		if(currentPage == 0)
			return "";

		
		//○ 페이지에 삽입할 링크 url 준비
		if(listUrl.indexOf("?") != -1)
			listUrl = listUrl + "&";
		else
			listUrl = listUrl + "?";

		
		//○ 페이징 처리
		currentPageSetup = (currentPage / numPerBlock) * numPerBlock;
		//-- currentPageSetup = 표시할 첫 페이지 - 1 (예: 『prev　11, 12, 13 ... 19, 20　next』 에서 '10')

		if(currentPage % numPerBlock == 0)
			currentPageSetup = currentPageSetup - numPerBlock;

		
		//① 1 페이지 
		if((totalPage > numPerBlock) && (currentPageSetup > 0))
		{
			strList.append(" <a href='" + listUrl + "pageNum=1'>◀◀</a>");
		}

		//② Prev
		n = currentPage - numPerBlock;
		//-- n : 해당 페이지만큼 앞으로 가기 위한 변수  (예: 24페이지면 → 14페이지로 이동)

		// 10개 이상 일때 Prev 표시
		if((totalPage > numPerBlock) && (currentPageSetup > 0))
			strList.append(" <a href='" + listUrl + "pageNum=" + n + "'>Prev</a>");
		//-- currentPageSetup 이 0 보다 큰 경우는
		//   이미 페이지가 10 이상이라는 의미이며
		//   이 때, 현재 페이지(currentPage) 가 11 이상일 경우
		//   『Prev』를 붙이기 위한 구문.
		//-- 『Prev』를 클릭할 경우
		//   n 변수 페이지로 이동하는데 12에서 『Prev』 할 경우
		//   2 페이지가 되고, 22에서 『Prev』 할 경우 12 페이지가
		//	 될 수 있도록 처리하는 방식이다. 

		//③ 각 페이지 바로가기
		page = currentPageSetup + 1;
		//-- 『+1』을 수행하는 이유는
		//	 앞에서 currentPageSetup 에서 10을 가져왔다면
		//   10 부터 시작하는 것이 아니라 
		//   바로가기 페이지는 11부터 시작해야 하기 때문이다.

		while ((page <= totalPage) && (page <= currentPageSetup + numPerBlock))
		{
			if(page == currentPage)
				strList.append(" <span style='color:orange; font-weight:bold; font-size:20px;'>" + page + "</span>");
			else
				strList.append(" <a id= 'pageId' href='" + listUrl + "pageNum=" + page + "'>" + page + "</a>");

			page++;
		}

		//④ Next
		n = currentPage + numPerBlock;
		//-- n : 해당 페이지만큼 뒤로 가기 위한 변수  (예: 14페이지면 → 24페이지로 이동)

		if((totalPage - currentPageSetup) > numPerBlock)
			strList.append(" <a href='" + listUrl + "pageNum=" + n + "'>Next</a>");

		//⑤ 마지막 페이지 
		if((totalPage > numPerBlock) && (currentPageSetup + numPerBlock) < totalPage)
			strList.append(" <a href='" + listUrl + "pageNum=" + totalPage + "'>" + "▶▶" + "</a>");

		
		//○ 완성된 페이징 문자열 반환 
		return strList.toString();
	}// end pageIndexList(int currentPage, int totalPage, String listUrl)

}
