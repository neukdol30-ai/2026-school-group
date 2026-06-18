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

    public List<CommentDto> searchAdminCommentList(CommentDto commentDto) {

        if (commentDto.getPage() == null || commentDto.getPage() < 1) {
            commentDto.setPage(1);
        }

        if (commentDto.getSize() == null || commentDto.getSize() < 1) {
            commentDto.setSize(10);
        }

        int offset = (commentDto.getPage() - 1) * commentDto.getSize();

        commentDto.setOffset(offset);

        return commentDao.searchAdminCommentList(commentDto);
    }

    public int countAdminCommentList(CommentDto commentDto) {
        return commentDao.countAdminCommentList(commentDto);
    }

    public int adminDeleteComment(Integer no) {
        return commentDao.adminDeleteComment(no);
    }

    public List<CommentDto> findAdminCommentListByBoardNo(Integer boardNo) {
        return commentDao.findAdminCommentListByBoardNo(boardNo);
    }
}