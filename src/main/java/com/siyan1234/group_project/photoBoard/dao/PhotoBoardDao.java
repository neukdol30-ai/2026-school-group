package com.siyan1234.group_project.photoBoard.dao;

import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PhotoBoardDao {

    // 기존 전체 목록 조회
    List<PhotoBoardDto> findAll(@Param("keyword") String keyword);

    // 인증게시판 목록 페이징 조회
    List<PhotoBoardDto> findAllPaging(@Param("keyword") String keyword,
                                      @Param("offset") int offset,
                                      @Param("size") int size);

    // 인증게시판 전체 게시글 개수 조회
    int countPhotoBoard(@Param("keyword") String keyword);

    Integer findRestaurantByName(@Param("name") String name);

    void insertRestaurant(PhotoBoardDto photoBoardDto);

    void write(PhotoBoardDto photoBoardDto);

    PhotoBoardDto findByNo(@Param("no") int no);

    void increaseHit(@Param("no") int no);

    void update(PhotoBoardDto photoBoardDto);

    void deleteLikeByBoardNo(@Param("boardNo") int boardNo);

    void deleteCommentByBoardNo(@Param("boardNo") int boardNo);

    void deleteBoard(@Param("no") int no);

    int countLike(@Param("boardNo") int boardNo,
                  @Param("memberNo") int memberNo);

    void insertLike(@Param("boardNo") int boardNo,
                    @Param("memberNo") int memberNo);

    void deleteLike(@Param("boardNo") int boardNo,
                    @Param("memberNo") int memberNo);

    void increaseLikeCount(@Param("boardNo") int boardNo);

    void decreaseLikeCount(@Param("boardNo") int boardNo);

    void adminDeleteComment(@Param("commentNo") int commentNo);
    List<PhotoBoardDto> searchAdminPhotoBoardList(PhotoBoardDto photoBoardDto);

    int countAdminPhotoBoardList(PhotoBoardDto photoBoardDto);

    PhotoBoardDto findAdminPhotoBoardByNo(@Param("no") Integer no);
}