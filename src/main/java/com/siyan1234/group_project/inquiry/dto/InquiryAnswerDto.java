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

public class InquiryAnswerDto {
    private Integer no;
    private Integer inquiryNo;
    private Integer adminNo;
    private String content;
    private String isDeleted;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
