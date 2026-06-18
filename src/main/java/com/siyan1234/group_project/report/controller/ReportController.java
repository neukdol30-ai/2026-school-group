package com.siyan1234.group_project.report.controller;

import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.report.dto.ReportDto;
import com.siyan1234.group_project.report.service.ReportService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/write")
    public String writeForm(
            @RequestParam(required = false) String targetType,
            @RequestParam(required = false) Integer targetNo,
            @RequestParam(required = false) String returnUrl,
            Model model,
            HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        if (targetType == null
                || targetNo == null
                || (!"BOARD".equals(targetType)
                && !"COMMENT".equals(targetType))) {
            return "redirect:/";
        }

        model.addAttribute("targetType", targetType);
        model.addAttribute("targetNo", targetNo);
        model.addAttribute("returnUrl", returnUrl);

        return "report/write";
    }

    @PostMapping("/write")
    public String write(
            ReportDto reportDto,
            @RequestParam(required = false) String returnUrl,
            HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        reportDto.setReporterNo(loginUser.getNo());

        int result =
                reportService.insertReport(reportDto);

        if (returnUrl != null && !returnUrl.trim().isEmpty()) {
            return "redirect:" + returnUrl + getResultParam(result);
        }

        return "redirect:/";
    }

    private String getResultParam(int result) {

        if (result == -1) {
            return "&report=duplicate";
        }

        if (result <= 0) {
            return "&report=fail";
        }

        return "&report=success";
    }
}