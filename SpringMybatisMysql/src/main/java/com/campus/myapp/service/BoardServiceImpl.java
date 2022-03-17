package com.campus.myapp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.campus.myapp.dao.BoardDAO;
import com.campus.myapp.vo.BoardVO;
import com.campus.myapp.vo.PagingVO;

@Service
public class BoardServiceImpl implements BoardService {
	@Inject
	BoardDAO dao;
	
	@Override
	public int boardInsert(BoardVO vo) {
		return dao.boardInsert(vo);
	}

	@Override
	public List<BoardVO> boardList(PagingVO pvo) {
		// TODO Auto-generated method stub
		return dao.boardList(pvo);
	}

	@Override
	public int totalRecord(PagingVO pvo) {
		return dao.totalRecord(pvo);
	}

	@Override
	public BoardVO boardSelect(int no) {
		// TODO Auto-generated method stub
		return dao.boardSelect(no);
	}

	@Override
	public void hitCount(int no) {
		dao.hitCount(no);
	}

	@Override
	public int boardUpdate(BoardVO vo) {
		// TODO Auto-generated method stub
		return dao.boardUpdate(vo);
	}

	@Override
	public int boardDelete(int no, String userid) {
		return dao.boardDelete(no, userid);
	}

}
