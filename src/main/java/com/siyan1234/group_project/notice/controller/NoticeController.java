package com.siyan1234.group_project.notice.controller;

import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.notice.dto.NoticeDto;
import com.siyan1234.group_project.notice.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping({"", "/", "/list"})
    public String list(Model model, HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        model.addAttribute("noticeList", noticeService.getList());

        return "notice/list";
    }

    @GetMapping("/view")
    public String view(@RequestParam("no") int no,
                       Model model,
                       HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        model.addAttribute("notice", noticeService.getNotice(no));

        return "notice/view";
    }

    @GetMapping("/write")
    public String write(HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        return "notice/write";
    }

    @PostMapping("/write")
    public String writeProcess(@ModelAttribute NoticeDto noticeDto,
                               HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        noticeDto.setMemberNo(loginUser.getNo());

        noticeService.write(noticeDto);

        return "redirect:/notice/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("no") int no,
                       Model model,
                       HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        model.addAttribute("notice", noticeService.findByNo(no));

        return "notice/edit";
    }

    @PostMapping("/edit")
    public String editProcess(@ModelAttribute NoticeDto noticeDto,
                              HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        noticeService.update(noticeDto);

        return "redirect:/notice/view?no=" + noticeDto.getNo();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("no") int no,
                         HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        noticeService.delete(no);

        return "redirect:/notice/list";
    }
}