package com.siyan1234.group_project.photoBoard.service;

import com.siyan1234.group_project.photoBoard.dao.PhotoBoardDao;
import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoBoardService {

    private final PhotoBoardDao photoBoardDao;

    public List<PhotoBoardDto> getList(String keyword) {
        return photoBoardDao.findAll(keyword);
    }
    // 인증게시판 목록 페이징 조회
    public List<PhotoBoardDto> getList(String keyword, int page, int size) {

        if (page < 1) {
            page = 1;
        }

        if (size < 1) {
            size = 10;
        }

        int offset = (page - 1) * size;

        return photoBoardDao.findAllPaging(keyword, offset, size);
    }

    // 인증게시판 전체 게시글 개수 조회
    public int getTotalCount(String keyword) {
        return photoBoardDao.countPhotoBoard(keyword);
    }

    @Transactional
    public void write(PhotoBoardDto photoBoardDto) {

        Integer existingRestaurantNo = photoBoardDao.findRestaurantByName(photoBoardDto.getRestaurantName());

        if (existingRestaurantNo != null) {
            photoBoardDto.setRestaurantNo(existingRestaurantNo);
        } else {
            photoBoardDao.insertRestaurant(photoBoardDto);
        }

        photoBoardDao.write(photoBoardDto);
    }

    public PhotoBoardDto getBoard(int no) {
        return photoBoardDao.findByNo(no);
    }

    public void update(PhotoBoardDto photoBoardDto) {
        photoBoardDao.update(photoBoardDto);
    }


    @Transactional
    public void delete(int no) {
        photoBoardDao.deleteLikeByBoardNo(no);
        photoBoardDao.deleteCommentByBoardNo(no);
        photoBoardDao.deleteBoard(no);
    }

    public void like(int boardNo, int memberNo) {

        int count = photoBoardDao.countLike(boardNo, memberNo);

        if (count > 0) {
            photoBoardDao.deleteLike(boardNo, memberNo);
            photoBoardDao.decreaseLikeCount(boardNo);
            return;
        }

        photoBoardDao.insertLike(boardNo, memberNo);
        photoBoardDao.increaseLikeCount(boardNo);
    }

    public void increaseHit(int no) {
        photoBoardDao.increaseHit(no);
    }

    @Transactional
    public void adminDeleteBoard(int boardNo) {
        photoBoardDao.deleteLikeByBoardNo(boardNo);
        photoBoardDao.deleteCommentByBoardNo(boardNo);
        photoBoardDao.deleteBoard(boardNo);
    }

    public void adminDeleteComment(int commentNo) {
        photoBoardDao.adminDeleteComment(commentNo);
    }
}