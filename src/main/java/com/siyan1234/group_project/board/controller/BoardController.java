package com.siyan1234.group_project.board.controller;

import com.siyan1234.group_project.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String listBoard(Model model) {
        model.addAttribute("boardList", boardService.listBoard());
        return "board/list";
    }

    @GetMapping("/view")
    public String view(@RequestParam int no, Model model) {
        model.addAttribute("board", boardService.viewBoard(no));

        return "board/view";
    }
}
