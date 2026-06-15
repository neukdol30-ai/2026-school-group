package com.siyan1234.group_project.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {
    private int no;
    private int memberNo;
    private String boardType;
    private String category;
    private Integer restaurantNo;
    private String title;
    private String content;
    private Double rating;
    private Double tasteRating;
    private Double facilityRating;
    private Double serviceRating;
    private int hit;
    private int likeCount;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    // board 테이블에 없는 화면 표시 (JOIN 결과)
    private String memberId;
    private String name;
}