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

    // 홈 화면 최신 글 5건 조회
    List<BoardDto> recentPosts();

    // admin 계정 관리자 페이지 추가
    List<BoardDto> searchAdminBoardList(BoardDto boardDto);
    int countAdminBoardList(BoardDto boardDto);
    int adminDeleteBoard(Integer no);

}

