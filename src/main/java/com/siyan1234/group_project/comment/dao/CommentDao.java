package com.siyan1234.group_project.comment.dao;

import com.siyan1234.group_project.comment.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    List<CommentDto> listComment(int boardNo); // 특정 글(boardNo)의 댓글 전체 목록

    void writeComment(CommentDto commentDto); // 댓글 저장

    void deleteComment(int no); // 1건 삭제
}
