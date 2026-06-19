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
    private String memberId; // 댓글 작성자 ID

    // 댓글이 달린 게시글 제목
    private String boardTitle;

    // 관리자 게시글 관리용 추가 필드
    private String isDeleted;
    private String searchType;
    private String keyword;
    private String searchDeleted;
    private Integer page;
    private Integer size;
    private Integer offset;

    private String boardType;
    private String searchBoardType;
}
