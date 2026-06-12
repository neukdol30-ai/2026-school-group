package com.siyan1234.group_project.board.service;

import com.siyan1234.group_project.board.dao.BoardDao;
import com.siyan1234.group_project.board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;

    public List<BoardDto> listBoard() {
        return boardDao.listBoard();
    }

    // 글 1건 조회 + 조회수 증가 메서드
    public BoardDto viewBoard(int no) {
        boardDao.updateHit(no); // 1. 조회수 1 up (DB hit 컬럼 +1)

        return boardDao.viewBoard(no);
        // 2. 올라간 조회수 반영된 상태로 글 1건 가져옴 => 호출한 Controller에게 돌려보냄
    }

    // 글 저장 메서드
    public void writeBoard(BoardDto boardDto) {
        // 1: 평점 3개 Dto
        Double taste = boardDto.getTasteRating();
        Double facility = boardDto.getFacilityRating();
        Double service = boardDto.getServiceRating();

        // 2: 모두 입력된 경우 평균 계산
        if (taste != null && facility != null && service != null) {
            // 3: 평균(맛, 시설, 서비스)
            double avg = (taste + facility + service) / 3;
            // 4: 소수 첫째 자리 반올림
            boardDto.setRating(Math.round(avg * 10) / 10.0);
        }
        boardDao.writeBoard(boardDto);
    }
}
