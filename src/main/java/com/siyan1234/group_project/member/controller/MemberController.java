package com.siyan1234.group_project.member.controller;

import com.siyan1234.group_project.member.dto.LoginDto;
import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
///비밀번호 암호화///
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
///비밀번호 암호화///
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(){ return "member/signup";}

    @PostMapping("/signup")
    public String signupProcess(MemberDto memberDto){
        String password = memberDto.getPassword();
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";
        if (!password.matches(regex)){
            return "redirect:/member/signup";
        }

        int result = memberService.signup(memberDto);

        if (result == 0){
            return "redirect:/member/signup?error=duplicate";
        }
        if(result == -1){
            return "redirect:/member/signup?error=phone";
        }
        if (result == -2){
            return "redirect:/member/signup?error=password";
        }
        return "redirect:/member/login";
    }

    @PostMapping("/id-check")
    @ResponseBody
    public Map<String, Boolean> idCheck(String memberId){

        boolean duplicate =
                memberService.idCheck(memberId);

        Map<String, Boolean> map = new HashMap<>();

        map.put("isDuplicate", duplicate);

        return map;
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @PostMapping("/login")
    public String login(
            LoginDto loginDto,
            HttpSession session, RedirectAttributes redirectAttributes){
        MemberDto member = memberService.login(loginDto);

        if (member == null){
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "아이디 또는 비밀번호를 다시 확인해주세요.");
            return "redirect:/member/login";
        }
        session.setAttribute("loginUser",member);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(
            HttpSession session){

        session.invalidate();

        return "redirect:/?logout=true";
    }

    @GetMapping("/mypage")
    public String mypage(
            HttpSession session,
            Model model,
            String error,
            String success){

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null){
            return "redirect:/member/login";
        }
        model.addAttribute("member",loginUser);
        model.addAttribute("error",error);
        model.addAttribute("success", success);
        return "member/mypage";
    }

    @PostMapping("/delete")
    public String deleteMember(String password,String reason,HttpSession session){
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null){
            return "redirect:/member/login";
        }

        MemberDto member = memberService.findByNo(loginUser.getNo());
        if (member == null){
            session.invalidate();
            return "redirect:/member/login";
        }

        if (!passwordEncoder.matches(
                password,
                member.getPassword()
        )){
            return "redirect:/member/withdraw?error=password";
        }
        // 탈퇴사유 미입력 처리
        if(reason == null || reason.trim().isEmpty()){
            reason = "미입력";
        }

        // 탈퇴사유 저장
        memberService.insertWithdrawReason(
                member.getMemberId(),
                reason
        );

        int result = memberService.deleteMember(loginUser.getNo());

        if (result > 0){
            session.invalidate();
            return "redirect:/";
        }
        return "redirect:/member/mypage";
    }

    @GetMapping("/withdraw")
    public String withdraw(){
        return "member/withdraw";
    }

    @PostMapping("/update")
    public String updateMember(
            MemberDto memberDto,
            String currentPassword,
            HttpSession session){

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null){
            return "redirect:/member/login";
        }
        String result =
                memberService.updateMember(
                        loginUser.getNo(),
                        currentPassword,
                        memberDto
                );
        if (!"SUCCESS".equals(result)){
            return "redirect:/member/mypage?error="
                    +result;
        }

        MemberDto updateMember =
                memberService.findByNo(loginUser.getNo());
        session.setAttribute(
                "loginUser",
                updateMember
        );
        return "redirect:/member/mypage?success=true";
    }
}
