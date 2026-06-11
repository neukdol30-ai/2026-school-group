package com.siyan1234.group_project.board.service;

import com.siyan1234.group_project.board.dao.BoardDao;
import com.siyan1234.group_project.board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;

    public List<BoardDto> listBoard() {
        return boardDao.listBoard();
    }

    // 글 1건 조회 + 조회수 증가 메서드
    public BoardDto viewBoard(int no) {
        boardDao.updateHit(no); // 1. 조회수 1 up (DB hit 컬럼 +1)

        return boardDao.viewBoard(no);
        // 2. 올라간 조회수 반영된 상태로 글 1건 가져옴 => 호출한 Controller에게 돌려보냄
    }
}
