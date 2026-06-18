package com.siyan1234.group_project.photoBoard.controller;

import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import com.siyan1234.group_project.photoBoard.dto.photoBoardCommentDto;
import com.siyan1234.group_project.photoBoard.service.PhotoBoardService;
import com.siyan1234.group_project.photoBoard.service.photoBoardCommentService;
import jakarta.servlet.http.HttpSession;
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

    private boolean isAdmin(MemberDto loginUser) {
        return loginUser != null
                && loginUser.getRole() != null
                && loginUser.getRole().equalsIgnoreCase("ADMIN");
    }

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword,
                       Model model,
                       HttpSession session) {
        model.addAttribute("menu","photo");

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        List<PhotoBoardDto> boardList = photoBoardService.getList(keyword);

        model.addAttribute("boardList", boardList);
        model.addAttribute("keyword", keyword);

        return "photoBoard/list";
    }

    @GetMapping("/write")
    public String write(HttpSession session, Model model) {

        model.addAttribute("menu","photo");

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        return "photoBoard/write";
    }

    @PostMapping("/write")
    public String writeProcess(PhotoBoardDto photoBoardDto,
                               @RequestParam("file") MultipartFile file,
                               HttpSession session) throws IOException {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        photoBoardDto.setMemberNo(loginUser.getNo());



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
        } else {
            photoBoardDto.setImageUrl(null);
        }

        photoBoardService.write(photoBoardDto);

        return "redirect:/photoBoard/list";
    }

    @GetMapping("/view")
    public String view(@RequestParam int no, Model model, HttpSession session) {
        model.addAttribute("menu","photo");

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        photoBoardService.increaseHit(no);

        PhotoBoardDto board = photoBoardService.getBoard(no);

        if (board == null) {
            return "redirect:/photoBoard/list";
        }

        List<photoBoardCommentDto> commentList = commentService.getList(no);

        model.addAttribute("board", board);
        model.addAttribute("commentList", commentList);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("isAdmin", isAdmin(loginUser));

        return "photoBoard/view";
    }

    @GetMapping("/update")
    public String update(@RequestParam int no, Model model, HttpSession session) {
        model.addAttribute("menu","photo");

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        PhotoBoardDto board = photoBoardService.getBoard(no);

        if (board == null) {
            return "redirect:/photoBoard/list";
        }

        if (board.getMemberNo() != loginUser.getNo() && !isAdmin(loginUser)) {
            return "redirect:/photoBoard/view?no=" + no;
        }

        model.addAttribute("board", board);

        return "photoBoard/update";
    }

    @PostMapping("/update")
    public String updateProcess(PhotoBoardDto photoBoardDto, HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        PhotoBoardDto board = photoBoardService.getBoard(photoBoardDto.getNo());

        if (board == null) {
            return "redirect:/photoBoard/list";
        }

        if (board.getMemberNo() != loginUser.getNo() && !isAdmin(loginUser)) {
            return "redirect:/photoBoard/view?no=" + photoBoardDto.getNo();
        }

        photoBoardService.update(photoBoardDto);

        return "redirect:/photoBoard/view?no=" + photoBoardDto.getNo();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int no, HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        PhotoBoardDto board = photoBoardService.getBoard(no);

        if (board == null) {
            return "redirect:/photoBoard/list";
        }

        if (board.getMemberNo() != loginUser.getNo() && !isAdmin(loginUser)) {
            return "redirect:/photoBoard/view?no=" + no;
        }

        photoBoardService.delete(no);

        return "redirect:/photoBoard/list";
    }

    @GetMapping("/like")
    public String like(@RequestParam int no, HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        photoBoardService.like(no, loginUser.getNo());

        return "redirect:/photoBoard/view?no=" + no;
    }

    @PostMapping("/comment/write")
    public String commentWrite(photoBoardCommentDto commentDto, HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        commentDto.setMemberNo(loginUser.getNo());
        commentService.write(commentDto);

        return "redirect:/photoBoard/view?no=" + commentDto.getBoardNo();
    }

    @GetMapping("/comment/delete")
    public String commentDelete(@RequestParam int no,
                                @RequestParam int boardNo,
                                HttpSession session) {

        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        if (isAdmin(loginUser)) {
            photoBoardService.adminDeleteComment(no);
        } else {
            commentService.delete(no, loginUser.getNo());
        }

        return "redirect:/photoBoard/view?no=" + boardNo;
    }
}