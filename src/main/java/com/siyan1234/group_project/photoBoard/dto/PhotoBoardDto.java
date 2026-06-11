package com.siyan1234.group_project.photoBoard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PhotoBoardDto {
    // board 테이블
    private int no;              // 게시글 번호
    private int memberNo;        // 회원 번호
    private String boardType;    // FREE / REVIEW
    private int restaurantNo;    // 맛집 번호
    private String title;        // 제목
    private String content;      // 내용
    private double rating;       // 평점
    private int hit;             // 조회수
    private int likeCount;       // 좋아요 수
    private String regdate;      // 작성일
    private String updateDate;   // 수정일

    // restaurant 테이블
    private String restaurantName; // 맛집명
    private String region;         // 지역
    private String category;       // 음식종류
    private String address;        // 주소
    private String phone;          // 전화번호
    private String description;    // 소개글

    // board_image 테이블
    private String imageUrl;       // 사진 경로

    // member 테이블
    private String memberName;     // 작성자 이름
}

