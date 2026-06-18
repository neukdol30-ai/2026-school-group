package com.siyan1234.group_project.report.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDto {

    private Integer no;
    private Integer reporterNo;
    private String targetType;
    private Integer targetNo;
    private String reason;
    private String content;
    private String status;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    private String reporterId;
    private String reporterName;

    private String targetTitle;
    private String targetContent;

    private String searchStatus;
    private String searchTargetType;
    private String searchType;
    private String keyword;

    private Integer page;
    private Integer size;
    private Integer offset;
}