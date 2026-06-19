package com.siyan1234.group_project.member.service;

import com.siyan1234.group_project.member.dao.MemberDao;
import com.siyan1234.group_project.member.dto.LoginDto;
import com.siyan1234.group_project.member.dto.MemberAdminDto;
import com.siyan1234.group_project.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
///비밀번호 암호화///
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    /// 비밀번호 암호화///
    private final BCryptPasswordEncoder passwordEncoder;

    /// 비밀번호 암호화 ///
    public int signup(MemberDto memberDto) {

        if (idCheck(memberDto.getMemberId())) {
            return 0;
        }

        String phone = memberDto.getPhone();

        if (phone == null) {
            return -1;
        }

        phone = phone.replaceAll("-", "");

        if (!phone.matches("^010[0-9]{8}$")) {
            return -1;
        }

        phone = phone.replaceFirst(
                "(010)([0-9]{4})([0-9]{4})",
                "$1-$2-$3"
        );

        memberDto.setPhone(phone);

        if (memberDto.getPassword() == null
                || !memberDto.getPassword().matches(
                "^(?=.*[A-Za-z])(?=.*\\d).{8,}$"
        )) {
            return -2;
        }

        memberDto.setPassword(
                passwordEncoder.encode(memberDto.getPassword())
        );

        return memberDao.signup(memberDto);
    }

    public String loginCheck(LoginDto loginDto) {

        MemberDto member =
                memberDao.findByMemberId(loginDto.getMemberId());

        if (member == null) {
            return "NOT_FOUND";
        }

        if ("Y".equals(member.getIsDeleted())) {
            return "DELETED";
        }

        if ("SUSPENDED".equals(member.getStatus())) {
            return "SUSPENDED";
        }

        if (!passwordEncoder.matches(
                loginDto.getPassword(),
                member.getPassword()
        )) {
            return "PASSWORD_FAIL";
        }

        return "SUCCESS";
    }

    public MemberDto login(LoginDto loginDto) {

        return memberDao.findByMemberId(loginDto.getMemberId());
    }

    /// //////////////////////////////////////////////////////////////////////

    public boolean idCheck(String memberId) {
        Integer result = memberDao.idCheck(memberId);
        return result != null && result > 0;
    }


    public int deleteMember(Integer no) {
        return memberDao.deleteMember(no);
    }

    public MemberDto findByNo(Integer no) {
        return memberDao.findByNo(no);
    }

    public int insertWithdrawReason(String memberId, String reason) {
        return memberDao.insertWithdrawReason(memberId, reason);
    }

    public String updateMember(
            Integer memberNo,
            String currentPassword,
            MemberDto memberDto) {

        MemberDto member = memberDao.findByNo(memberNo);

        if (member == null) {
            return "NOT_FOUND";
        }
        //현재 비밀번호 검증
        if (!passwordEncoder.matches(
                currentPassword,
                member.getPassword()
        )) {
            return "WRONG_CURRENT_PASSWORD";
        }
        //전화번호 형식 검사
        if (memberDto.getPhone() == null || !memberDto.getPhone().matches(
                "^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$"
        )) {
            return "INVALID_PHONE";
        }

        memberDto.setNo(memberNo);

        String password = memberDto.getPassword();

        //비밀번호 변경하는 경우
        if (password != null && !password.isBlank()) {
            //8자 이상 영문+숫자 검사
            if (!password.matches(
                    "^(?=.*[A-Za-z])(?=.*\\d).{8,}$"
            )) {
                return "INVALID_PASSWORD";
            }
            //기존 비밀번호와 동일한지 검사
            if (passwordEncoder.matches(
                    password,
                    member.getPassword()
            )) {
                return "SAME_PASSWORD";
            }
            memberDto.setPassword(passwordEncoder.encode(password));
        } else {
            //비밀번호 변경 안함
            memberDto.setPassword(member.getPassword());
        }
        int result = memberDao.updateMember(memberDto);
        if (result > 0){
            return "SUCCESS";
        }
        return "FAIL";
    }

    public List<MemberAdminDto> searchMemberStatistics(
            MemberAdminDto memberAdminDto){

        if(memberAdminDto.getPage() == null
                || memberAdminDto.getPage() < 1){

            memberAdminDto.setPage(1);
        }

        if(memberAdminDto.getSize() == null
                || memberAdminDto.getSize() < 1){

            memberAdminDto.setSize(10);
        }

        int offset =
                (memberAdminDto.getPage() - 1)
                        * memberAdminDto.getSize();

        memberAdminDto.setOffset(offset);

        return memberDao.searchMemberStatistics(memberAdminDto);
    }

    public int countMemberStatistics(
            MemberAdminDto memberAdminDto){

        return memberDao.countMemberStatistics(memberAdminDto);
    }
    public int suspendMember(Integer no) {
        return memberDao.suspendMember(no);
    }

    public int releaseMember(Integer no) {
        return memberDao.releaseMember(no);
    }

    public int adminDeleteMember(Integer no) {
        return memberDao.adminDeleteMember(no);
    }
}
