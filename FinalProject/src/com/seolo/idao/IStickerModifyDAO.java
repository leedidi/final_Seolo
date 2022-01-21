package com.seolo.idao;

import java.util.ArrayList;

import com.seolo.dto.StickerDTO;

public interface IStickerModifyDAO
{
	public ArrayList<String> listBookmarkSticker(int chbNo);
	
	public ArrayList<String> listWriterSticker(int checkNo);
	
	public ArrayList<String> listLocalBookmarkSticker(int lobNo);
	
	public ArrayList<StickerDTO> listSticker(String acNo);
	
	public int bookmarkNo(StickerDTO dto);
	
	public int addMyCheckSticker(StickerDTO dto);
	public int addCheckSticker(StickerDTO dto);
	
	public int myCheckTagNo();
	public int checkTagNo();
	public int localTagNo();
	
	public int maxStickerNo();
	public int addNewSticker(StickerDTO dto);
	
	public ArrayList<StickerDTO> deleteListBookmarkSticker(int chbNo);
	public ArrayList<StickerDTO> deleteListWriterSticker(int checkNo);
	public ArrayList<StickerDTO> deleteListLocalBookmarkSticker(int lobNo);
	
	public int deleteWriterSticker(int tagNo);
	public int deleteBookmarkSticker(int tagNo);
	public int deleteLocalSticker(int tagNo);
	
	public int writerStickerNo(String tagNo);
	public int bookmarkStickerNo(String tagNo);
	public int localStickerNo(String tagNo);
	
	public int validSticker(int stickerNo);
	public int deleteInvalidSticker(int stickerNo);
}
