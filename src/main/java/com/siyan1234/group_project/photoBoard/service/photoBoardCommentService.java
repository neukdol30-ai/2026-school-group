package com.siyan1234.group_project.photoBoard.service;

import com.siyan1234.group_project.photoBoard.dao.photoBoardCommentDao;
import com.siyan1234.group_project.photoBoard.dto.photoBoardCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class photoBoardCommentService {

    private final photoBoardCommentDao commentDao;

    public List<photoBoardCommentDto> getList(int boardNo) {
        return commentDao.findByBoardNo(boardNo);
    }

    public void write(photoBoardCommentDto commentDto) {
        commentDao.write(commentDto);
    }
}