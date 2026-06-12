package com.siyan1234.group_project.inquiry.controller;

import com.siyan1234.group_project.inquiry.dto.InquiryDto;
import com.siyan1234.group_project.inquiry.service.InquiryService;
import com.siyan1234.group_project.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping("/write")
    public String writeForm(HttpSession session){
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null){
            return "redirect:/memeber/login";
        }
        return "inquiry/write";
    }

    @PostMapping("/write")
    public String writeProcess(InquiryDto inquiryDto,HttpSession session){
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null){
            return "redirect:/member/login";
        }
        inquiryDto.setMemberNo(
                loginUser.getNo()
        );
        inquiryService.insertInquiry(inquiryDto);
        return "redirect:/inquiry/list";
    }

    @GetMapping("/list")
    public String myInquiryList(
            HttpSession session,
            Model model){
        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null){
            return "redirect:/member/login";
        }
        model.addAttribute(
                "inquiryList",
                inquiryService.findMyInquiryList(
                        loginUser.getNo()
                )
        );
        return "inquiry/list";
    }

    @GetMapping("/detail")
    public String detail(
            Integer no,
            HttpSession session,
            Model model){
        MemberDto loginUser =
                (MemberDto) session.getAttribute(
                        "loginUser"
                );
        if (loginUser == null){
            return "redirect:/memeber/login";
        }
        model.addAttribute(
                "inquiry",
                inquiryService.findByNo(no)
        );
        model.addAttribute(
                "answer",
                inquiryService.findAnswerByInquiryNo(no)
        );
        return "inquiry-detail";
    }
}
