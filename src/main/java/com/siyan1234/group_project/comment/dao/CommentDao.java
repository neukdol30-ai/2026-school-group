package com.siyan1234.group_project.comment.dao;

import com.siyan1234.group_project.comment.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {
    List<CommentDto> listComment(int boardNo); // 특정 글(boardNo)의 댓글 전체 목록

    void writeComment(CommentDto commentDto); // 댓글 저장

    void deleteComment(@Param("no") int no,
                       @Param("memberNo") int memberNo);
    List<CommentDto> searchAdminCommentList(CommentDto commentDto);
    int countAdminCommentList(CommentDto commentDto);
    int adminDeleteComment(Integer no);
    List<CommentDto> findAdminCommentListByBoardNo(Integer boardNo);
}


