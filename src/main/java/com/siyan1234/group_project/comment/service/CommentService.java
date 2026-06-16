package com.siyan1234.group_project.comment.service;

import com.siyan1234.group_project.comment.dao.CommentDao;
import com.siyan1234.group_project.comment.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;

    public List<CommentDto> listComment(int boardNo) {
        return commentDao.listComment(boardNo);
    }

    public void writeComment(CommentDto commentDto) {
        commentDao.writeComment(commentDto);
    }

    public void deleteComment(int no, int memberNo) {
        commentDao.deleteComment(no, memberNo);
    }
}
