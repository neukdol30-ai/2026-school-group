package com.siyan1234.group_project.board.service;

import com.siyan1234.group_project.board.dao.BoardDao;
import com.siyan1234.group_project.board.dto.BoardDto;
import com.siyan1234.group_project.board.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;

    public List<BoardDto> listBoard(PageDto pageDto) {
        int total = boardDao.countBoard(pageDto); // 1: 검색 조건 반영 전체 글 수

        pageDto.calculate(total); // 2: offset과 totalPage 계산.

        return boardDao.listBoard(pageDto); // 3: 계산 끝 => pageDto 목록 10건 조회
    }

    // 글 1건 조회 + 조회수 증가 메서드
    public BoardDto viewBoard(int no) {
        boardDao.updateHit(no); // 1. 조회수 1 up (DB hit 컬럼 +1)

        return boardDao.viewBoard(no);
        // 2. 올라간 조회수 반영된 상태로 글 1건 가져옴 => 호출한 Controller에게 돌려보냄
    }

    // 글 저장 메서드
    public void writeBoard(BoardDto boardDto) {
        setAverageRating(boardDto); // 1. 평점 계산
        boardDao.writeBoard(boardDto); // 2. INSERT 실행
    }

    // 수정 화면 (조회수 안 올림)
    public BoardDto getBoard(int no) {
        return boardDao.viewBoard(no);
    }

    // 글 수정
    public void editBoard(BoardDto boardDto) {
        setAverageRating(boardDto); // 1. 바뀐 평점으로 다시 계산
        boardDao.editBoard(boardDto); // 2. update 실행
    }

    // 글 삭제
    public void deleteBoard(int no, int memberNo) {
        boardDao.deleteBoard(no, memberNo);
    }

    // 평균 평점 계산 공용 메서드
    private void setAverageRating(BoardDto boardDto) {
        // 1: 평점 3개 꺼내기
        Double taste = boardDto.getTasteRating();
        Double facility = boardDto.getFacilityRating();
        Double service = boardDto.getServiceRating();
        // 2: 셋 다 있으면 평균 계산(참이면 실행)
        if (taste != null && facility != null && service != null) {
            // 3: 평균(맛, 시설, 서비스)
            double avg = (taste + facility + service) / 3;
            // 4: 소수 첫째 자리 반올림
            boardDto.setRating(Math.round(avg * 10) / 10.0);
        }
    }

    public List<BoardDto> recentPosts() {
        return boardDao.recentPosts();
    }

    // admin 계정 관리자 페이지 추가
    public List<BoardDto> searchAdminBoardList(BoardDto boardDto) {
        if (boardDto.getPage() == null || boardDto.getPage() < 1) {
            boardDto.setPage(1);
        }
        if (boardDto.getSize() == null || boardDto.getSize() < 1) {
            boardDto.setSize(10);
        }
        int offset = (boardDto.getPage() - 1) * boardDto.getSize();
        boardDto.setOffset(offset);
        return boardDao.searchAdminBoardList(boardDto);
    }

    public int countAdminBoardList(BoardDto boardDto) {
        return boardDao.countAdminBoardList(boardDto);
    }

    public int adminDeletedBoard(Integer no) {
        return boardDao.adminDeleteBoard(no);
    }
}