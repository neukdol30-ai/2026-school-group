package com.siyan1234.group_project.photoBoard.service;

import com.siyan1234.group_project.comment.dto.CommentDto;
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

    public void delete(int no, int memberNo) {
        commentDao.delete(no, memberNo);
    }
    public List<CommentDto> searchAdminPhotoCommentList(CommentDto commentDto) {

        if (commentDto.getPage() == null || commentDto.getPage() < 1) {
            commentDto.setPage(1);
        }

        if (commentDto.getSize() == null || commentDto.getSize() < 1) {
            commentDto.setSize(10);
        }

        int offset =
                (commentDto.getPage() - 1) * commentDto.getSize();

        commentDto.setOffset(offset);

        return commentDao.searchAdminPhotoCommentList(commentDto);
    }

    public int countAdminPhotoCommentList(CommentDto commentDto) {
        return commentDao.countAdminPhotoCommentList(commentDto);
    }
}