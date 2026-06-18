package com.siyan1234.group_project.photoBoard.dao;

import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PhotoBoardDao {

    List<PhotoBoardDto> findAll(@Param("keyword") String keyword);

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