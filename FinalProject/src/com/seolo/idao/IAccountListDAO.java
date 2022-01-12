/*=========================
   IAccountListDAO.java
   - 회원 출력 인터페이스
==========================*/

package com.seolo.idao;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.seolo.dto.AccountListDTO;


public interface IAccountListDAO
{
	// 게시물 목록
	public ArrayList<AccountListDTO> list(@Param("start") int start, @Param("end") int end);
	public int count();
}
