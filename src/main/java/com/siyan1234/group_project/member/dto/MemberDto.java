package com.siyan1234.group_project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MemberDto {
    private Integer no;
    private String memberId;
    private String password;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String detailAddress;
    private String role;
    private String isDeleted;
    private LocalDateTime regDate;

    // 관리자 회원 검색용
    private String searchType;
    private String keyword;

    // 관리자 필터용
    private String searchRole;
    private String searchDeleted;

    // 페이징용
    private Integer page;
    private Integer size;
    private Integer offset;

    private String status;
    private String searchStatus;
}
