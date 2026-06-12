package com.siyan1234.group_project.photoBoard.controller;

import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import com.siyan1234.group_project.photoBoard.service.PhotoBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    public String writeProcess(PhotoBoardDto photoBoardDto,
                               @RequestParam("file") MultipartFile file) throws IOException {

        photoBoardDto.setMemberNo(1);
        photoBoardDto.setRestaurantNo(1);

        if (!file.isEmpty()) {
            String uploadPath = "D:/upload/";

            File folder = new File(uploadPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String originalFileName = file.getOriginalFilename();
            String savedFileName = UUID.randomUUID() + "_" + originalFileName;

            File saveFile = new File(uploadPath + savedFileName);
            file.transferTo(saveFile);

            photoBoardDto.setImageUrl("/upload/" + savedFileName);
        }

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

    @GetMapping("/like")
    public String like(@RequestParam int no) {
        photoBoardService.like(no);
        return "redirect:/photoBoard/view?no=" + no;
    }
}