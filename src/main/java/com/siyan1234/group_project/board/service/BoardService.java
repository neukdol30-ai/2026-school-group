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
}
