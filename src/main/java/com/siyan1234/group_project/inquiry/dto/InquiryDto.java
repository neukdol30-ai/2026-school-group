package com.siyan1234.group_project.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InquiryDto {
    private Integer no;
    private Integer memberNo;
    private String title;
    private String content;
    private String phone;
    private String category;
    private String status;
    private String isRead;
    private String isDeleted;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
