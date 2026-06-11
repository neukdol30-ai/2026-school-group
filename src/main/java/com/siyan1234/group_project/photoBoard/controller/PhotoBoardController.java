package com.siyan1234.group_project.photoBoard.controller;

import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import com.siyan1234.group_project.photoBoard.service.PhotoBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/photoBoard")
public class PhotoBoardController {


    private final PhotoBoardService photoBoardService;

    // 사진 리뷰 게시판 목록
    @GetMapping("/list")
    public String list(Model model) {

        List<PhotoBoardDto> boardList =
                photoBoardService.getList();

        model.addAttribute("boardList", boardList);

        return "photoBoard/list";
    }

    // 사진 리뷰 게시글 작성 페이지
    @GetMapping("/write")
    public String write() {
        return "photoBoard/write";
    }

    // 사진 리뷰 게시글 상세보기
    @GetMapping("/view")
    public String view() {
        return "photoBoard/view";
    }

}
