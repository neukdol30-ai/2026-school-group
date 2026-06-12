package com.siyan1234.group_project.comment.dto;

import lombok.Data;

@Data
public class CommentDto {
    private int no; // 댓글 번호
    private int boardNo; // 어느 글의 댓글
    private int memberNo; // 누가 쓴 댓글
    private String content;
    private String regDate;
    private String name;
}
