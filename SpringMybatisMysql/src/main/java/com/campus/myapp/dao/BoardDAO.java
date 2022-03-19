package com.campus.myapp.dao;

import java.util.List;

import com.campus.myapp.vo.BoardVO;
import com.campus.myapp.vo.PagingVO;

public interface BoardDAO {
	public int boardInsert(BoardVO vo);
	//글 목록
	public List<BoardVO> boardList(PagingVO pvo);
	//총 레코드 수
	public int totalRecord(PagingVO pvo);
	//글 내용 보기
	public BoardVO boardSelect(int no);
	//조회수
	public void hitCount(int no);
	//글 수정
	public int boardUpdate(BoardVO vo);
	//글 삭제
	public int boardDelete(int no, String userid);
	
	//글 삭제2
	public int boardDeleteSelect(int no);
	public int boardMultiDelete(BoardVO vo);
}
