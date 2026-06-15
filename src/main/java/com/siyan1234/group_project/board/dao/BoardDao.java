package com.siyan1234.group_project.board.dao;

import com.siyan1234.group_project.board.dto.BoardDto;
import com.siyan1234.group_project.board.dto.PageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDao {
    List<BoardDto> listBoard(PageDto pageDto);

    int countBoard(PageDto pageDto);

    BoardDto viewBoard(int no);
    void updateHit(int no);

    // 글 저장
    void writeBoard(BoardDto boardDto);

    // 글 수정
    void editBoard(BoardDto boardDto);

    // 글 삭제
    void deleteBoard(@Param("no") int no,
                     @Param("memberNo") int memberNo);

}

