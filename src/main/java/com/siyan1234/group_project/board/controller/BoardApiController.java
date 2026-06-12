package com.siyan1234.group_project.board.controller;

import com.siyan1234.group_project.comment.dto.CommentDto;
import com.siyan1234.group_project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/api")
public class BoardApiController {
    private final CommentService commentService; // 댓글 관련 처리

    @GetMapping("/comment/list")
    public List<CommentDto> listComment(@RequestParam int boardNo) {
        return commentService.listComment(boardNo);
    }

    @PostMapping("/comment/write")
    public void writeComment(@RequestBody CommentDto commentDto) {
        if (commentDto.getContent() == null || commentDto.getContent().trim().isEmpty()) {
            return;
        }

        commentDto.setMemberNo(1); // 로그인 연동 전 임시 1번 회원 댓글 처리
        commentDto.setContent(commentDto.getContent().trim()); // 댓글 앞뒤 공백 제거 후 다시 저장
        commentService.writeComment(commentDto); // service에게 댓글 저장 요청
    }

    @PostMapping("/comment/delete")
    public void deleteComment(@RequestParam int no) {
        commentService.deleteComment(no);
    }


}
