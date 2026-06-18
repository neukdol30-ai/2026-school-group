package com.siyan1234.group_project.notice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {

    private int no;
    private int memberNo;
    private String title;
    private String content;
    private int hit;
    private String regdate;
    private String updateDate;
    private String isDeleted;
}