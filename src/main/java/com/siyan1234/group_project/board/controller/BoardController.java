package com.siyan1234.group_project.board.controller;

import com.siyan1234.group_project.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
