package com.siyan1234.group_project.photoBoard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoBoardDto {

    private int no;
    private int memberNo;
    private String boardType;
    private int restaurantNo;

    private String title;
    private String content;
    private double rating;

    private int hit;
    private int likeCount;

    private String regdate;
    private String updateDate;
    private String isDeleted;

    private String imageUrl;

    private String restaurantName;
    private String region;
    private String category;
    private String address;
    private String phone;
    private String description;

    private String memberId;
    private String memberName;

    private String searchDeleted;
    private String searchType;
    private String keyword;

    private Integer page;
    private Integer size;
    private Integer offset;
}