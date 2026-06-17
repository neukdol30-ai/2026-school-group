package com.siyan1234.group_project.notice.controller;

import com.siyan1234.group_project.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping({"", "/", "/list"})
    public String list(HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        return "notice/list";
    }
}