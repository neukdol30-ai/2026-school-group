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

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null){
            return "redirect:/member/login";
        }

        return "inquiry/write";
    }

    @PostMapping("/write")
    public String writeProcess(
            InquiryDto inquiryDto,
            HttpSession session){

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null){
            return "redirect:/member/login";
        }

        // 제목 검증
        if(inquiryDto.getTitle() == null
                || inquiryDto.getTitle().trim().isEmpty()){
            return "redirect:/inquiry/write?error=title";
        }

        // 내용 검증
        if(inquiryDto.getContent() == null
                || inquiryDto.getContent().trim().isEmpty()){
            return "redirect:/inquiry/write?error=content";
        }

        // 카테고리 검증
        if(inquiryDto.getCategory() == null
                || inquiryDto.getCategory().trim().isEmpty()){
            return "redirect:/inquiry/write?error=category";
        }

        String phone = inquiryDto.getPhone();

        String regex =
                "^01[0-9]-\\d{3,4}-\\d{4}$";

        if (phone == null || !phone.matches(regex)){
            return "redirect:/inquiry/write?error=phone";
        }

        inquiryDto.setMemberNo(
                loginUser.getNo()
        );

        int result =
                inquiryService.insertInquiry(inquiryDto);

        if(result <= 0){
            return "redirect:/inquiry/write?error=fail";
        }

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

        return "inquiry/inquiry-list";
    }

    @GetMapping("/detail")
    public String detail(
            Integer no,
            HttpSession session,
            Model model){

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null){
            return "redirect:/member/login";
        }

        if(no == null){
            return "redirect:/inquiry/list";
        }

        InquiryDto inquiry = inquiryService.findMyInquiry(
                no,
                loginUser.getNo()
        );
        if (inquiry == null){
            return "redirect:/inquiry/list";
        }

        model.addAttribute(
                "inquiry",
                inquiry
        );

        model.addAttribute(
                "answer",
                inquiryService.findAnswerByInquiryNo(no)
        );

        return "inquiry/inquiry-detail";
    }
}
