package com.siyan1234.group_project.member.dao;

import com.siyan1234.group_project.member.dto.LoginDto;
import com.siyan1234.group_project.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

    int signup(MemberDto dto);

    MemberDto login(LoginDto loginDto);

    MemberDto findByNo(Integer no);

    List<MemberDto> findAllMembers();

    Integer idCheck(String memberId);

    int deleteMember(Integer no);

    int updateMember(MemberDto memberDto);

    int insertWithdrawReason(String memberId, String reason);

}
