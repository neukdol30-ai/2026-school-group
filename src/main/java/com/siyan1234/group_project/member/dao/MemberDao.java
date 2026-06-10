package com.siyan1234.group_project.member.dao;

import com.siyan1234.group_project.member.dto.LoginDto;
import com.siyan1234.group_project.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    int signup(MemberDto dto);

    MemberDto login(LoginDto loginDto);

    MemberDto findByNo(Integer no);

    Integer idCheck(String memberId);

    int deleteMember(Integer no);

    int updateMember(MemberDto memberDto);

}
