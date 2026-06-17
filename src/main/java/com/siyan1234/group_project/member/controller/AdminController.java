package com.siyan1234.group_project.member.controller;

import com.siyan1234.group_project.board.dto.BoardDto;
import com.siyan1234.group_project.board.service.BoardService;
import com.siyan1234.group_project.comment.dto.CommentDto;
import com.siyan1234.group_project.comment.service.CommentService;
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
    // 필드 추가
    private final BoardService boardService;
    private final CommentService commentService;

    /* =========================
       공통 ADMIN 체크
    ========================= */
    private boolean isAdmin(HttpSession session) {

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        return loginUser != null && "ADMIN".equals(loginUser.getRole());
    }

    /* =========================
       관리자 메인
    ========================= */
    @GetMapping("")
    public String adminPage(HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        return "admin/admin";
    }

    /* =========================
       회원 목록
    ========================= */
    @GetMapping("/member-list")
    public String memberList(HttpSession session, Model model) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        model.addAttribute(
                "memberList",
                memberService.findAllMembers()
        );
        ///보드 연동후 사용할 코드 memberStatistics
//        model.addAttribute(
//        "memberList",
//        memberService.memberStatistics());

        return "admin/member-list";
    }

    /* =========================
       문의 목록
    ========================= */
    @GetMapping("/inquiry-list")
    public String inquiryList(
            InquiryDto inquiryDto,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

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
            HttpSession session,
            Model model
    ) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

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

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

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

    // 게시글 목록 (관리자)
    @GetMapping("/board-list")
    public String boardList(
            BoardDto boardDto,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        if (boardDto.getPage() == null || boardDto.getPage() < 1) {
            boardDto.setPage(1);
        }

        boardDto.setSize(10);

        int totalCount = boardService.countAdminBoardList(boardDto);
        int totalPage = (int) Math.ceil((double) totalCount / boardDto.getSize());

        model.addAttribute("boardList", boardService.searchAdminBoardList(boardDto));
        model.addAttribute("search", boardDto);
        model.addAttribute("currentPage", boardDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);
        return "admin/board-list";
    }

    // 게시글 삭제 (관리자)
    @GetMapping("/board-delete")
    public String boardDelete(
            @RequestParam int no,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        boardService.adminDeletedBoard(no);

        return "redirect:/admin/board-list";
    }

    // 댓글 목록 (관리자)
    @GetMapping("/comment-list")
    public String commentList(
            CommentDto commentDto,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        if (commentDto.getPage() == null || commentDto.getPage() < 1) {
            commentDto.setPage(1);
        }

        commentDto.setSize(10);

        int totalCount = commentService.countAdminCommentList(commentDto);

        int totalPage = (int) Math.ceil((double) totalCount / commentDto.getSize());

        model.addAttribute("commentList", commentService.searchAdminCommentList(commentDto));
        model.addAttribute("search", commentDto);
        model.addAttribute("currentPage", commentDto.getPage());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);

        return "admin/comment-list";
    }

    /* =========================
   댓글 삭제 (관리자)
========================= */
    @GetMapping("/comment-delete")
    public String commentDelete(
            @RequestParam int no,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        commentService.adminDeleteComment(no);

        return "redirect:/admin/comment-list";
    }

}

