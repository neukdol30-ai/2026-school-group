package com.siyan1234.group_project.member.service;

import com.siyan1234.group_project.member.dao.MemberDao;
import com.siyan1234.group_project.member.dto.LoginDto;
import com.siyan1234.group_project.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    public int signup(MemberDto memberDto) {
        return memberDao.signup(memberDto);
    }

    public boolean idCheck(String memberId){
        Integer result = memberDao.idCheck(memberId);
        return result != null && result > 0;
    }

    public MemberDto login(LoginDto loginDto){
        return memberDao.login(loginDto);
    }

    public int deleteMember(Integer no){
        return memberDao.deleteMember(no);
    }

    public MemberDto findByNo(Integer no){
        return memberDao.findByNo(no);
    }
}
