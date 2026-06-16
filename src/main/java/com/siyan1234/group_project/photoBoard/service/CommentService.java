package com.siyan1234.group_project.photoBoard.service;

import com.siyan1234.group_project.photoBoard.dao.CommentDao;
import com.siyan1234.group_project.photoBoard.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentDao;

    public List<CommentDto> getList(int boardNo) {
        return commentDao.findByBoardNo(boardNo);
    }

    public void write(CommentDto commentDto) {
        commentDao.write(commentDto);
    }
}