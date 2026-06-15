package com.siyan1234.group_project.board.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeDao {

    int checkLike(@Param("boardNo") int boardNo,
                  @Param("memberNo") int memberNo);

    void insertLike(@Param("boardNo") int boardNo,
                    @Param("memberNo") int memberNo);

    void deleteLike(@Param("boardNo") int boardNo,
                    @Param("memberNo") int memberNo);

    void incrementLikeCount(int boardNO);

    void decrementLikeCount(int boardNo);

    int getLikeCount(int boardNo);

}
