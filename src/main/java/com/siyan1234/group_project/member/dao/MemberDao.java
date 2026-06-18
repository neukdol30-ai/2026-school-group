package com.siyan1234.group_project.member.dao;

import com.siyan1234.group_project.member.dto.LoginDto;
import com.siyan1234.group_project.member.dto.MemberAdminDto;
import com.siyan1234.group_project.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberDao {

    int signup(MemberDto dto);

    MemberDto findByMemberId(String memberId);

    MemberDto findByNo(Integer no);

    Integer idCheck(String memberId);

    int deleteMember(Integer no);

    int updateMember(MemberDto memberDto);

    int insertWithdrawReason(
            @Param("memberId") String memberId,
            @Param("reason") String reason);
    List<MemberAdminDto> searchMemberStatistics(MemberAdminDto memberAdminDto);

    int countMemberStatistics(MemberAdminDto memberAdminDto);

}
