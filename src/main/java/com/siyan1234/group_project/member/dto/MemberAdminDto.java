package com.siyan1234.group_project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MemberAdminDto {
    private Integer no;
    private String memberId;
    private String name;
    private String role;
    private String isDeleted;
    private Integer boardCount;
    private Integer commentCount;
    private Integer likeCount;
}
