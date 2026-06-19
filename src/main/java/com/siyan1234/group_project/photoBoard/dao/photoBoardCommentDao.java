package com.siyan1234.group_project.photoBoard.dao;

import com.siyan1234.group_project.comment.dto.CommentDto;
import com.siyan1234.group_project.photoBoard.dto.photoBoardCommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface photoBoardCommentDao {

    List<photoBoardCommentDto> findByBoardNo(@Param("boardNo") int boardNo);

    void write(photoBoardCommentDto commentDto);

    void delete(@Param("no") int no,
                @Param("memberNo") int memberNo);

    List<CommentDto> searchAdminPhotoCommentList(CommentDto commentDto);

    int countAdminPhotoCommentList(CommentDto commentDto);
}