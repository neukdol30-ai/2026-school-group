package com.siyan1234.group_project.board.controller;

import com.siyan1234.group_project.board.dto.BoardDto;
import com.siyan1234.group_project.board.dto.PageDto;
import com.siyan1234.group_project.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.siyan1234.group_project.member.dto.MemberDto;

import java.lang.reflect.Member;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String listBoard(PageDto pageDto, Model model, HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        model.addAttribute("boardList", boardService.listBoard(pageDto)); // 목록 10건 화면 전달

        model.addAttribute("page", pageDto);

        return "board/list";
    }

    @GetMapping("/view")
    public String view(@RequestParam int no, Model model) {
        model.addAttribute("board", boardService.viewBoard(no));

        return "board/view";
    }

    @GetMapping("/write") // 글쓰기 화면
    public String writeForm(HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        return "board/write";
    }

    @PostMapping("/write") // 글 저장
    public String write(BoardDto boardDto, HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        boardDto.setMemberNo(loginUser.getNo());
        boardService.writeBoard(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping("/edit") // 수정 화면
    public String editForm(@RequestParam int no, Model model) {
        model.addAttribute("board", boardService.getBoard(no)); // 수정 화면은 조회수 올라가면 X => getBoard 사용
        return "board/edit";
    }

    // 수정 저장(실행 후 상세 보기로 이동)
    @PostMapping("/edit")
    public String edit(BoardDto boardDto) {
        boardService.editBoard(boardDto);

        return "redirect:/board/view?no=" + boardDto.getNo();
    }

    // 삭제(실행 후 목록으로)
    @GetMapping("/delete")
    public String delete(@RequestParam int no, HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        boardService.deleteBoard(no);

        return "redirect:/board/list";
    }
}
