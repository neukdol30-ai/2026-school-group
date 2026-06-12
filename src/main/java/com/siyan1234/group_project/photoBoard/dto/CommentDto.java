package com.siyan1234.group_project.photoBoard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private int no;
    private int boardNo;
    private int memberNo;
    private String content;
    private String regdate;
}