package com.siyan1234.group_project.member.controller;

import com.siyan1234.group_project.board.dto.BoardDto;
import com.siyan1234.group_project.board.service.BoardService;
import com.siyan1234.group_project.comment.dto.CommentDto;
import com.siyan1234.group_project.comment.service.CommentService;
import com.siyan1234.group_project.inquiry.dto.InquiryAnswerDto;
import com.siyan1234.group_project.inquiry.dto.InquiryDto;
import com.siyan1234.group_project.inquiry.service.InquiryService;
import com.siyan1234.group_project.member.dto.MemberAdminDto;
import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.member.service.MemberService;
import com.siyan1234.group_project.report.dto.ReportDto;
import com.siyan1234.group_project.report.service.ReportService;
import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import com.siyan1234.group_project.photoBoard.service.PhotoBoardService;
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
    private final BoardService boardService;
    private final CommentService commentService;
    private final ReportService reportService;
    private final PhotoBoardService photoBoardService;

    @GetMapping("")
    public String adminPage() {
        return "admin/admin";
    }

    @GetMapping("/member-list")
    public String memberList(
            MemberAdminDto memberAdminDto,
            Model model) {

        if (memberAdminDto.getPage() == null
                || memberAdminDto.getPage() < 1) {
            memberAdminDto.setPage(1);
        }

        memberAdminDto.setSize(10);

        int totalCount =
                memberService.countMemberStatistics(memberAdminDto);

        int totalPage =
                (int) Math.ceil((double) totalCount / memberAdminDto.getSize());

        model.addAttribute(
                "memberList",
                memberService.searchMemberStatistics(memberAdminDto)
        );

        model.addAttribute("search", memberAdminDto);
        model.addAttribute("currentPage", memberAdminDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/member-list";
    }

    @GetMapping("/inquiry-list")
    public String inquiryList(
            InquiryDto inquiryDto,
            Model model) {

        if (inquiryDto.getPage() == null || inquiryDto.getPage() < 1) {
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

    @GetMapping("/inquiry-detail")
    public String inquiryDetail(
            @RequestParam(required = false) Integer no,
            Model model) {

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
            HttpSession session) {

        if (dto == null
                || dto.getInquiryNo() == null
                || dto.getContent() == null
                || dto.getContent().trim().isEmpty()) {
            return "redirect:/admin/inquiry-list";
        }

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        dto.setAdminNo(loginUser.getNo());

        int result =
                inquiryService.insertAnswer(dto);

        if (result <= 0) {
            return "redirect:/admin/inquiry-detail?no="
                    + dto.getInquiryNo()
                    + "&error=answerFail";
        }

        return "redirect:/admin/inquiry-detail?no="
                + dto.getInquiryNo()
                + "&success=true";
    }

    @PostMapping("/inquiry-answer-update")
    public String updateAnswer(InquiryAnswerDto dto) {

        if (dto == null
                || dto.getNo() == null
                || dto.getInquiryNo() == null
                || dto.getContent() == null
                || dto.getContent().trim().isEmpty()) {
            return "redirect:/admin/inquiry-list";
        }

        int result =
                inquiryService.updateAnswer(dto);

        if (result <= 0) {
            return "redirect:/admin/inquiry-detail?no="
                    + dto.getInquiryNo()
                    + "&error=answerUpdateFail";
        }

        return "redirect:/admin/inquiry-detail?no="
                + dto.getInquiryNo()
                + "&success=answerUpdate";
    }

    @PostMapping("/inquiry-delete")
    public String inquiryDelete(Integer no) {

        if (no == null) {
            return "redirect:/admin/inquiry-list";
        }

        inquiryService.adminDeleteInquiry(no);

        return "redirect:/admin/inquiry-list";
    }

    @GetMapping("/board-list")
    public String boardList(
            BoardDto boardDto,
            Model model) {

        if (boardDto.getPage() == null || boardDto.getPage() < 1) {
            boardDto.setPage(1);
        }

        boardDto.setSize(10);

        int totalCount =
                boardService.countAdminBoardList(boardDto);

        int totalPage =
                (int) Math.ceil((double) totalCount / boardDto.getSize());

        model.addAttribute(
                "boardList",
                boardService.searchAdminBoardList(boardDto)
        );

        model.addAttribute("search", boardDto);
        model.addAttribute("currentPage", boardDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/board-list";
    }

    @PostMapping("/board-delete")
    public String boardDelete(Integer no) {

        if (no == null) {
            return "redirect:/admin/board-list";
        }

        boardService.adminDeleteBoard(no);

        return "redirect:/admin/board-list";
    }

    @GetMapping("/board-detail")
    public String boardDetail(
            @RequestParam(required = false) Integer no,
            Model model) {

        if (no == null) {
            return "redirect:/admin/board-list";
        }

        BoardDto board =
                boardService.findAdminBoardByNo(no);

        if (board == null) {
            return "redirect:/admin/board-list";
        }

        model.addAttribute("board", board);

        model.addAttribute(
                "commentList",
                commentService.findAdminCommentListByBoardNo(no)
        );

        return "admin/board-detail";
    }

    @PostMapping("/board-detail-comment-delete")
    public String boardDetailCommentDelete(
            Integer no,
            Integer boardNo) {

        if (no == null || boardNo == null) {
            return "redirect:/admin/board-list";
        }

        commentService.adminDeleteComment(no);

        return "redirect:/admin/board-detail?no=" + boardNo;
    }

    @GetMapping("/comment-list")
    public String commentList(
            CommentDto commentDto,
            Model model) {

        if (commentDto.getPage() == null || commentDto.getPage() < 1) {
            commentDto.setPage(1);
        }

        commentDto.setSize(10);

        int totalCount =
                commentService.countAdminCommentList(commentDto);

        int totalPage =
                (int) Math.ceil((double) totalCount / commentDto.getSize());

        model.addAttribute(
                "commentList",
                commentService.searchAdminCommentList(commentDto)
        );

        model.addAttribute("search", commentDto);
        model.addAttribute("currentPage", commentDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/comment-list";
    }

    @PostMapping("/comment-delete")
    public String commentDelete(Integer no) {

        if (no == null) {
            return "redirect:/admin/comment-list";
        }

        commentService.adminDeleteComment(no);

        return "redirect:/admin/comment-list";
    }

    @GetMapping("/report-list")
    public String reportList(
            ReportDto reportDto,
            Model model) {

        if (reportDto.getPage() == null || reportDto.getPage() < 1) {
            reportDto.setPage(1);
        }

        reportDto.setSize(10);

        int totalCount =
                reportService.countAdminReportList(reportDto);

        int totalPage =
                (int) Math.ceil((double) totalCount / reportDto.getSize());

        model.addAttribute(
                "reportList",
                reportService.searchAdminReportList(reportDto)
        );

        model.addAttribute("search", reportDto);
        model.addAttribute("currentPage", reportDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/report-list";
    }

    @GetMapping("/report-detail")
    public String reportDetail(
            @RequestParam(required = false) Integer no,
            Model model) {

        if (no == null) {
            return "redirect:/admin/report-list";
        }

        ReportDto report =
                reportService.findAdminReportByNo(no);

        if (report == null) {
            return "redirect:/admin/report-list";
        }

        model.addAttribute("report", report);

        return "admin/report-detail";
    }

    @PostMapping("/report-done")
    public String reportDone(Integer no) {

        if (no == null) {
            return "redirect:/admin/report-list";
        }

        reportService.updateReportDone(no);

        return "redirect:/admin/report-detail?no=" + no;
    }
    @PostMapping("/member-suspend")
    public String suspendMember(Integer no) {

        if (no == null) {
            return "redirect:/admin/member-list";
        }

        memberService.suspendMember(no);

        return "redirect:/admin/member-list";
    }

    @PostMapping("/member-release")
    public String releaseMember(Integer no) {

        if (no == null) {
            return "redirect:/admin/member-list";
        }

        memberService.releaseMember(no);

        return "redirect:/admin/member-list";
    }

    @PostMapping("/member-delete")
    public String adminDeleteMember(Integer no) {

        if (no == null) {
            return "redirect:/admin/member-list";
        }

        memberService.adminDeleteMember(no);

        return "redirect:/admin/member-list";
    }
    @GetMapping("/photo-board-list")
    public String photoBoardList(PhotoBoardDto photoBoardDto, Model model) {

        if (photoBoardDto.getPage() == null || photoBoardDto.getPage() < 1) {
            photoBoardDto.setPage(1);
        }

        photoBoardDto.setSize(10);

        int totalCount =
                photoBoardService.countAdminPhotoBoardList(photoBoardDto);

        int totalPage =
                (int) Math.ceil((double) totalCount / photoBoardDto.getSize());

        model.addAttribute(
                "photoBoardList",
                photoBoardService.searchAdminPhotoBoardList(photoBoardDto)
        );

        model.addAttribute("search", photoBoardDto);
        model.addAttribute("currentPage", photoBoardDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/photo-board-list";
    }

    @GetMapping("/photo-board-detail")
    public String photoBoardDetail(@RequestParam(required = false) Integer no,
                                   Model model) {

        if (no == null) {
            return "redirect:/admin/photo-board-list";
        }

        PhotoBoardDto board =
                photoBoardService.findAdminPhotoBoardByNo(no);

        if (board == null) {
            return "redirect:/admin/photo-board-list";
        }

        model.addAttribute("board", board);
        model.addAttribute(
                "commentList",
                commentService.findAdminCommentListByBoardNo(no)
        );

        return "admin/photo-board-detail";
    }

    @PostMapping("/photo-board-delete")
    public String photoBoardDelete(Integer no) {

        if (no == null) {
            return "redirect:/admin/photo-board-list";
        }

        photoBoardService.adminDeleteBoard(no);

        return "redirect:/admin/photo-board-list";
    }

    @GetMapping("/photo-comment-list")
    public String photoCommentList(CommentDto commentDto, Model model) {

        if (commentDto.getPage() == null || commentDto.getPage() < 1) {
            commentDto.setPage(1);
        }

        commentDto.setSize(10);

        int totalCount =
                commentService.countAdminPhotoCommentList(commentDto);

        int totalPage =
                (int) Math.ceil((double) totalCount / commentDto.getSize());

        model.addAttribute(
                "commentList",
                commentService.searchAdminPhotoCommentList(commentDto)
        );

        model.addAttribute("search", commentDto);
        model.addAttribute("currentPage", commentDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/photo-comment-list";
    }

    @PostMapping("/photo-comment-delete")
    public String photoCommentDelete(Integer no) {

        if (no == null) {
            return "redirect:/admin/photo-comment-list";
        }

        commentService.adminDeleteComment(no);

        return "redirect:/admin/photo-comment-list";
    }
}