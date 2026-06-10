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
    private String role;
    private LocalDateTime regDate;
}
