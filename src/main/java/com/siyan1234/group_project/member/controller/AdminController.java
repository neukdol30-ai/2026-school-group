package com.siyan1234.group_project.member.controller;

import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("")
    public String adminPage(HttpSession session){

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if(loginUser == null){
            return "redirect:/member/login";
        }

        if(!"ADMIN".equals(loginUser.getRole())){
            return "admin/access-denied";
        }

        return "admin/admin";
    }

    @GetMapping("/member-list")
    public String memberList(HttpSession session, Model model){
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())){
            return "redirect:/";
        }
        model.addAttribute("memberList", memberService.findAllMembers());

        ///보드 연동후 사용할 코드 memberStatistics
//        model.addAttribute("memberList", memberService.memberStatistics());
        return "admin/member-list";
    }
}
