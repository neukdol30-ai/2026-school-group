package com.siyan1234.group_project.board.controller;

import com.siyan1234.group_project.board.service.LikeService;
import com.siyan1234.group_project.comment.dto.CommentDto;
import com.siyan1234.group_project.comment.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/api")
public class BoardApiController {

    private final CommentService commentService; // 댓글 관련 처리
    private final LikeService likeService;

    @GetMapping("/comment/list")
    public List<CommentDto> listComment(@RequestParam int boardNo) {
        return commentService.listComment(boardNo);
    }

    @PostMapping("/comment/write")
    public void writeComment(@RequestBody CommentDto commentDto
                             HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return;
        }
        if (commentDto.getContent() == null
                || commentDto.getContent().trim().isEmpty()) {
            return;
        }
        commentDto.setMemberNo(loginUser.getNo()); // 로그인 연동 전 임시 1번 회원 댓글 처리
        commentDto.setContent(commentDto.getContent().trim()); // 댓글 앞뒤 공백 제거 후 다시 저장
        commentService.writeComment(commentDto); // service에게 댓글 저장 요청
    }

    @PostMapping("/comment/delete")
    public void deleteComment(@RequestParam int no) {
        commentService.deleteComment(no);
    }

    @GetMapping("/like")
    public Map<String, Object> getLikeInfo(
            @RequestParam int boardNo,
            HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return Map.of("liked", false, "likeCount",0 );
        }

        int memberNo = loginUser.getNo();
        return likeService.getLikeInfo(boardNo, memberNo);
    }

    @PostMapping("/like")
    public Map<String, Object> toggleLike(
            @RequestParam int boardNo,
            HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return Map.of("liked", false, "likeCount", 0);
        }

        int memberNo = loginUser.getNo();
        return likeService.toggleLike(boardNo, memberNo);
    }
}
