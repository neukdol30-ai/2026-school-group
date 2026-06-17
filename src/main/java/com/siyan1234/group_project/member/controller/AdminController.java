package com.siyan1234.group_project.member.controller;

import com.siyan1234.group_project.inquiry.dto.InquiryAnswerDto;
import com.siyan1234.group_project.inquiry.dto.InquiryDto;
import com.siyan1234.group_project.inquiry.service.InquiryService;
import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final InquiryService inquiryService;

    /* =========================
       관리자 메인
    ========================= */
    @GetMapping("")
    public String adminPage() {
        return "admin/admin";
    }

    /* =========================
       회원 목록
    ========================= */
    @GetMapping("/member-list")
    public String memberList(
            MemberDto memberDto,
            Model model) {
        if(memberDto.getPage() == null || memberDto.getPage() < 1){
            memberDto.setPage(1);
        }

        memberDto.setSize(10);

        int totalCount =
                memberService.countSearchMemberList(memberDto);

        int totalPage =
                (int) Math.ceil((double) totalCount / memberDto.getSize());

        model.addAttribute(
                "memberList",
                memberService.searchMemberList(memberDto)
        );

        model.addAttribute("search", memberDto);
        model.addAttribute("currentPage", memberDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/member-list";
    }

    /* =========================
       문의 목록
    ========================= */
    @GetMapping("/inquiry-list")
    public String inquiryList(
            InquiryDto inquiryDto,
            Model model) {

        if(inquiryDto.getPage() == null || inquiryDto.getPage() < 1){
            inquiryDto.setPage(1);
        }

        inquiryDto.setSize(10);

        int totalCount =
                inquiryService.countSearchInquiryList(inquiryDto);

        int totalPage =
                (int) Math.ceil((double) totalCount / inquiryDto.getSize());

        model.addAttribute(
                "inquiryList",
                inquiryService.searchInquiryList(inquiryDto)
        );

        model.addAttribute("search", inquiryDto);
        model.addAttribute("currentPage", inquiryDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/inquiry-list";
    }

    /* =========================
       문의 상세
    ========================= */
    @GetMapping("/inquiry-detail")
    public String inquiryDetail(
            @RequestParam(required = false) Integer no,
            Model model
    ) {
        if (no == null) {
            return "redirect:/admin/inquiry-list";
        }

        model.addAttribute(
                "inquiry",
                inquiryService.findByNo(no)
        );

        model.addAttribute(
                "answer",
                inquiryService.findAnswerByInquiryNo(no)
        );

        return "admin/inquiry-detail";
    }
    @PostMapping("/inquiry-answer")
    public String insertAnswer(
            InquiryAnswerDto dto,
            HttpSession session){

        if(dto == null
                || dto.getInquiryNo() == null
                || dto.getContent() == null
                || dto.getContent().trim().isEmpty()){

            return "redirect:/admin/inquiry-list";
        }

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        dto.setAdminNo(
                loginUser.getNo()
        );

        int result =
                inquiryService.insertAnswer(dto);

        if(result <= 0){
            return "redirect:/admin/inquiry-detail?no="
                    + dto.getInquiryNo()
                    + "&error=answerFail";
        }

        return "redirect:/admin/inquiry-detail?no="
                + dto.getInquiryNo()
                + "&success=true";
    }
}

