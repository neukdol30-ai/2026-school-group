package com.siyan1234.group_project.member.controller;

import com.siyan1234.group_project.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("recentPosts", boardService.recentPosts());
        return "index";
    }
}