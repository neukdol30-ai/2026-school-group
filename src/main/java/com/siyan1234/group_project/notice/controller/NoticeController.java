package com.siyan1234.group_project.notice.controller;

import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.notice.dto.NoticeDto;
import com.siyan1234.group_project.notice.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping({"", "/", "/list"})
    public String list(
            Model model,
            HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        model.addAttribute(
                "noticeList",
                noticeService.getList()
        );

        return "notice/list";
    }

    @GetMapping("/view")
    public String view(
            @RequestParam("no") int no,
            Model model,
            HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        NoticeDto notice =
                noticeService.getNotice(no);

        if (notice == null) {
            return "redirect:/notice/list";
        }

        model.addAttribute("notice", notice);

        return "notice/view";
    }

    @GetMapping("/write")
    public String writeForm(HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (!isAdmin(loginUser)) {
            return "redirect:/notice/list";
        }

        return "notice/write";
    }

    @PostMapping("/write")
    public String writeProcess(
            @ModelAttribute NoticeDto noticeDto,
            HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (!isAdmin(loginUser)) {
            return "redirect:/notice/list";
        }

        noticeDto.setMemberNo(loginUser.getNo());

        noticeService.write(noticeDto);

        return "redirect:/notice/list";
    }

    @GetMapping("/edit")
    public String editForm(
            @RequestParam("no") int no,
            Model model,
            HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (!isAdmin(loginUser)) {
            return "redirect:/notice/list";
        }

        NoticeDto notice =
                noticeService.findByNo(no);

        if (notice == null) {
            return "redirect:/notice/list";
        }

        model.addAttribute("notice", notice);

        return "notice/edit";
    }

    @PostMapping("/edit")
    public String editProcess(
            @ModelAttribute NoticeDto noticeDto,
            HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (!isAdmin(loginUser)) {
            return "redirect:/notice/list";
        }

        noticeService.update(noticeDto);

        return "redirect:/notice/view?no=" + noticeDto.getNo();
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam("no") int no,
            HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (!isAdmin(loginUser)) {
            return "redirect:/notice/list";
        }

        noticeService.delete(no);

        return "redirect:/notice/list";
    }

    private boolean isAdmin(MemberDto loginUser) {

        return loginUser != null
                && "ADMIN".equals(loginUser.getRole());
    }
}