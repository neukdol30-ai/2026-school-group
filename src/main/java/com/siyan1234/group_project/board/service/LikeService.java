package com.siyan1234.group_project.board.service;

import com.siyan1234.group_project.board.dao.LikeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeDao likeDao;

    // 추천 상태 조회
    public Map<String, Object> getLikeInfo(int boardNo, int memberNo) {
        boolean liked = likeDao.checkLike(boardNo, memberNo) > 0;

        int likeCount = likeDao.getLikeCount(boardNo);

        Map<String, Object> result = new HashMap<>();

        result.put("liked", liked);
        result.put("likeCount", likeCount);

        return result;
    }

    // 추천 토글
    public Map<String, Object> toggleLike(int boardNo, int memberNo) {
        boolean liked;

        if (likeDao.checkLike(boardNo, memberNo) == 0) {

            likeDao.insertLike(boardNo, memberNo);

            likeDao.incrementLikeCount(boardNo);

            liked = true;
        } else {

            likeDao.deleteLike(boardNo, memberNo);

            likeDao.decrementLikeCount(boardNo);

            liked = false;
        }

        int likeCount = likeDao.getLikeCount(boardNo);

        Map<String, Object> result = new HashMap<>();
        result.put("liked", liked);
        result.put("likeCount", likeCount);
        return result;
    }
}
