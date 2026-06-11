package com.siyan1234.group_project.photoBoard.service;

import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import com.siyan1234.group_project.photoBoard.dao.PhotoBoardDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PhotoBoardService {

    private final PhotoBoardDao photoBoardDao;

    // 사진 리뷰 게시판 목록
    public List<PhotoBoardDto> getList() {
        return photoBoardDao.findAll();
    }

    // 게시글 상세보기
    public PhotoBoardDto getBoard(int no) {
        return photoBoardDao.findByNo(no);
    }

    // 게시글 등록
    public void write(PhotoBoardDto photoBoardDto) {
        photoBoardDao.insert(photoBoardDto);
    }

    // 게시글 수정
    public void update(PhotoBoardDto photoBoardDto) {
        photoBoardDao.update(photoBoardDto);
    }

    // 게시글 삭제
    public void delete(int no) {
        photoBoardDao.delete(no);
    }
}
