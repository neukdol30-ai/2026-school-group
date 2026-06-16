package com.siyan1234.group_project.photoBoard.controller;

import com.siyan1234.group_project.photoBoard.dto.photoBoardCommentDto;
import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import com.siyan1234.group_project.photoBoard.service.photoBoardCommentService;
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
    private final photoBoardCommentService commentService;

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

        photoBoardService.increaseHit(no);

        PhotoBoardDto board = photoBoardService.getBoard(no);
        List<photoBoardCommentDto> commentList = commentService.getList(no);

        model.addAttribute("board", board);
        model.addAttribute("commentList", commentList);

        return "photoBoard/view";
    }

    @GetMapping("/update")
    public String update(@RequestParam int no, Model model) {
        PhotoBoardDto board = photoBoardService.getBoard(no);
        model.addAttribute("board", board);
        return "photoBoard/update";
    }

    @PostMapping("/update")
    public String updateProcess(PhotoBoardDto photoBoardDto) {
        photoBoardService.update(photoBoardDto);
        return "redirect:/photoBoard/view?no=" + photoBoardDto.getNo();
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

    @PostMapping("/comment/write")
    public String commentWrite(photoBoardCommentDto commentDto) {
        commentDto.setMemberNo(1);
        commentService.write(commentDto);

        return "redirect:/photoBoard/view?no=" + commentDto.getBoardNo();
    }
}