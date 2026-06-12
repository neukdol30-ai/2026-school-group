package com.siyan1234.group_project.photoBoard.controller;

import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import com.siyan1234.group_project.photoBoard.service.PhotoBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/photoBoard")
public class PhotoBoardController {

    private final PhotoBoardService photoBoardService;

    @GetMapping("/list")
    public String list(Model model) {
        List<PhotoBoardDto> boardList = photoBoardService.getList();
        model.addAttribute("boardList", boardList);
        return "photoBoard/list";
    }

    @GetMapping("/write")
    public String write() {
        return "photoBoard/write";
    }

    @PostMapping("/write")
    public String writeProcess(PhotoBoardDto photoBoardDto) {

        //System.out.println("title = " + photoBoardDto.getTitle());
        //System.out.println("content = " + photoBoardDto.getContent());
        //System.out.println("rating = " + photoBoardDto.getRating());

        photoBoardDto.setMemberNo(1);
        photoBoardDto.setRestaurantNo(1);

        photoBoardService.write(photoBoardDto);

        return "redirect:/photoBoard/list";
    }

    @GetMapping("/view")
    public String view(@RequestParam int no, Model model) {
        PhotoBoardDto board = photoBoardService.getBoard(no);
        model.addAttribute("board", board);
        return "photoBoard/view";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int no) {
        photoBoardService.delete(no);
        return "redirect:/photoBoard/list";
    }
}