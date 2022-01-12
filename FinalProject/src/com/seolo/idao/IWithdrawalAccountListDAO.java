/*=========================
   IWithdrawalAccountListDAO.java
   - 회원 출력 인터페이스
==========================*/

package com.seolo.idao;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.seolo.dto.WithdrawalAccountListDTO;


public interface IWithdrawalAccountListDAO
{
	// 게시물 목록
	public ArrayList<WithdrawalAccountListDTO> withdrawallist(@Param("start") int start, @Param("end") int end);
	public int withdrawalcount();
}
