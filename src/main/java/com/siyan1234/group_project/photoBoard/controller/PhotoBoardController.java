package com.siyan1234.group_project.photoBoard.controller;

import com.siyan1234.group_project.member.dto.MemberDto;
import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import com.siyan1234.group_project.photoBoard.dto.photoBoardCommentDto;
import com.siyan1234.group_project.photoBoard.service.PhotoBoardService;
import com.siyan1234.group_project.photoBoard.service.photoBoardCommentService;
import com.siyan1234.group_project.report.dto.ReportDto;
import com.siyan1234.group_project.report.service.ReportService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/photoBoard")
public class PhotoBoardController {

    // 게시글 목록, 작성, 수정, 삭제, 추천 기능을 Service에게 맡기기 위한 변수
    private final PhotoBoardService photoBoardService;

    // 댓글 목록, 작성, 삭제 기능을 Service에게 맡기기 위한 변수
    private final photoBoardCommentService commentService;

    // 신고 기능을 처리하는 Service
    private final ReportService reportService;

    // 현재 로그인한 사용자가 관리자인지 확인하는 메서드
    private boolean isAdmin(MemberDto loginUser) {

        // 로그인한 사용자가 있고, 권한 값이 비어있지 않고, 권한이 ADMIN이면 true 반환
        return loginUser != null
                && loginUser.getRole() != null
                && loginUser.getRole().equalsIgnoreCase("ADMIN");
    }

    // 인증게시판 목록 화면
    // 인증게시판 목록 화면
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword,
                       @RequestParam(defaultValue = "1") int page,
                       Model model,
                       HttpSession session) {

        // 화면에서 현재 메뉴가 인증게시판임을 표시하기 위해 menu 값을 넘김
        model.addAttribute("menu", "photo");

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 상태라면 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 검색어가 공백만 들어온 경우 null 처리
        if (keyword != null) {
            keyword = keyword.trim();

            if (keyword.isEmpty()) {
                keyword = null;
            }
        }

        // 한 페이지에 보여줄 게시글 수
        int size = 10;

        // page 값이 1보다 작으면 1페이지로 처리
        if (page < 1) {
            page = 1;
        }

        // 검색 조건에 맞는 전체 게시글 개수 조회
        int totalCount = photoBoardService.getTotalCount(keyword);

        // 전체 페이지 수 계산
        int totalPage = (int) Math.ceil((double) totalCount / size);

        // 게시글이 0개여도 화면 처리를 위해 최소 1페이지로 둠
        if (totalPage == 0) {
            totalPage = 1;
        }

        // 사용자가 존재하지 않는 큰 페이지 번호로 접근하면 마지막 페이지로 보정
        if (page > totalPage) {
            page = totalPage;
        }

        // 현재 페이지에 해당하는 게시글 10개만 조회
        List<PhotoBoardDto> boardList = photoBoardService.getList(keyword, page, size);

        // 게시글 목록을 HTML 화면으로 넘김
        model.addAttribute("boardList", boardList);

        // 검색어를 다시 화면으로 넘김
        model.addAttribute("keyword", keyword);

        // 현재 페이지 번호
        model.addAttribute("page", page);

        // 전체 페이지 수
        model.addAttribute("totalPage", totalPage);

        // 전체 게시글 수
        model.addAttribute("totalCount", totalCount);

        // 한 페이지당 게시글 수
        model.addAttribute("size", size);

        // templates/photoBoard/list.html 화면을 보여줌
        return "photoBoard/list";
    }

    // 글쓰기 화면으로 이동
    @GetMapping("/write")
    public String write(HttpSession session, Model model) {

        // 현재 메뉴가 인증게시판임을 화면에 알려줌
        model.addAttribute("menu", "photo");

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 글쓰기 화면에 접근할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // templates/photoBoard/write.html 화면을 보여줌
        return "photoBoard/write";
    }

    // 글쓰기 처리
    @PostMapping("/write")
    public String writeProcess(PhotoBoardDto photoBoardDto,
                               @RequestParam("file") MultipartFile file,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) throws IOException {

        // 세션에서 현재 로그인한 사용자 정보를 가져옴
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 글을 작성할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 게시글 작성자를 현재 로그인한 사용자의 회원 번호로 설정
        photoBoardDto.setMemberNo(loginUser.getNo());

        // 인증게시판은 사진 첨부가 필수이므로 파일이 없으면 저장하지 않음
        if (file == null || file.isEmpty()) {

            // redirect 후 인증게시판 목록 화면에서 한 번만 보여줄 오류 메시지 저장
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "인증게시판은 사진을 첨부해야 글을 작성할 수 있습니다."
            );

            // 사진이 없으면 글 저장 없이 인증게시판 목록으로 이동
            return "redirect:/photoBoard/list";
        }

        // 업로드된 이미지 파일을 저장할 실제 폴더 경로
        String uploadPath = "D:/upload/";

        // 업로드 폴더 객체 생성
        File folder = new File(uploadPath);

        // 업로드 폴더가 존재하지 않으면 새로 생성
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 사용자가 업로드한 원본 파일명
        String originalFileName = file.getOriginalFilename();

        // 파일명 중복을 막기 위해 UUID를 붙여 저장 파일명 생성
        String savedFileName = UUID.randomUUID() + "_" + originalFileName;

        // 실제 저장될 파일 객체 생성
        File saveFile = new File(uploadPath + savedFileName);

        // 업로드된 파일을 지정한 폴더에 저장
        file.transferTo(saveFile);

        // DB에는 실제 파일 자체가 아니라 브라우저에서 접근할 이미지 경로를 저장
        photoBoardDto.setImageUrl("/upload/" + savedFileName);

        // 게시글 정보와 이미지 경로를 DB에 저장
        photoBoardService.write(photoBoardDto);

        // 글 작성 완료 후 인증게시판 목록으로 이동
        return "redirect:/photoBoard/list";
    }

    // 게시글 상세보기
    @GetMapping("/view")
    public String view(@RequestParam int no,
                       @RequestParam(defaultValue = "true") boolean countHit,
                       Model model,
                       HttpSession session) {

        // 현재 메뉴가 인증게시판임을 화면에 알려줌
        model.addAttribute("menu", "photo");

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 상세보기를 볼 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // countHit이 true일 때만 조회수를 증가시킴
        // 추천, 댓글, 신고 후 돌아올 때는 countHit=false로 보내서 조회수 중복 증가를 막음
        if (countHit) {
            photoBoardService.increaseHit(no);
        }

        // 게시글 번호로 게시글 정보를 가져옴
        PhotoBoardDto board = photoBoardService.getBoard(no);

        // 게시글이 없으면 목록으로 이동
        if (board == null) {
            return "redirect:/photoBoard/list";
        }

        // 해당 게시글의 댓글 목록을 가져옴
        List<photoBoardCommentDto> commentList = commentService.getList(no);

        // 게시글 정보를 화면으로 넘김
        model.addAttribute("board", board);

        // 댓글 목록을 화면으로 넘김
        model.addAttribute("commentList", commentList);

        // 로그인한 사용자 정보를 화면으로 넘김
        model.addAttribute("loginUser", loginUser);

        // 관리자 여부를 화면으로 넘김
        model.addAttribute("isAdmin", isAdmin(loginUser));

        // templates/photoBoard/view.html 화면을 보여줌
        return "photoBoard/view";
    }

    // 게시글 수정 화면으로 이동
    @GetMapping("/update")
    public String update(@RequestParam int no,
                         Model model,
                         HttpSession session) {

        // 현재 메뉴가 인증게시판임을 화면에 알려줌
        model.addAttribute("menu", "photo");

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 수정 화면에 접근할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 수정할 게시글 정보를 가져옴
        PhotoBoardDto board = photoBoardService.getBoard(no);

        // 게시글이 없으면 목록으로 이동
        if (board == null) {
            return "redirect:/photoBoard/list";
        }

        // 작성자 본인도 아니고 관리자도 아니면 수정할 수 없으므로 상세보기로 이동
        if (board.getMemberNo() != loginUser.getNo() && !isAdmin(loginUser)) {
            return "redirect:/photoBoard/view?no=" + no;
        }

        // 수정 화면에 기존 게시글 정보를 넘김
        model.addAttribute("board", board);

        // templates/photoBoard/update.html 화면을 보여줌
        return "photoBoard/update";
    }

    // 게시글 수정 처리
    @PostMapping("/update")
    public String updateProcess(PhotoBoardDto photoBoardDto,
                                HttpSession session) {

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 수정할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 수정하려는 게시글 정보를 DB에서 다시 조회
        PhotoBoardDto board = photoBoardService.getBoard(photoBoardDto.getNo());

        // 게시글이 없으면 목록으로 이동
        if (board == null) {
            return "redirect:/photoBoard/list";
        }

        // 작성자 본인도 아니고 관리자도 아니면 수정하지 못하게 막음
        if (board.getMemberNo() != loginUser.getNo() && !isAdmin(loginUser)) {
            return "redirect:/photoBoard/view?no=" + photoBoardDto.getNo();
        }

        // 게시글 수정 처리
        photoBoardService.update(photoBoardDto);

        // 수정 후 상세보기로 돌아가되 조회수는 증가하지 않게 countHit=false를 붙임
        return "redirect:/photoBoard/view?no="
                + photoBoardDto.getNo()
                + "&countHit=false";
    }

    // 게시글 삭제 처리
    @PostMapping("/delete")
    public String delete(@RequestParam int no,
                         HttpSession session) {

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 삭제할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 삭제하려는 게시글 정보를 DB에서 조회
        PhotoBoardDto board = photoBoardService.getBoard(no);

        // 게시글이 없으면 목록으로 이동
        if (board == null) {
            return "redirect:/photoBoard/list";
        }

        // 작성자 본인도 아니고 관리자도 아니면 삭제하지 못하게 막음
        if (board.getMemberNo() != loginUser.getNo() && !isAdmin(loginUser)) {
            return "redirect:/photoBoard/view?no=" + no;
        }

        // 게시글 삭제 처리
        photoBoardService.delete(no);

        // 삭제 후 목록으로 이동
        return "redirect:/photoBoard/list";
    }

    // 추천 처리
    @GetMapping("/like")
    public String like(@RequestParam int no,
                       HttpSession session) {

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 추천할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 추천 또는 추천 취소 처리
        photoBoardService.like(no, loginUser.getNo());

        // 추천 후 상세보기로 돌아가되 조회수는 증가하지 않게 countHit=false를 붙임
        return "redirect:/photoBoard/view?no=" + no + "&countHit=false";
    }

    // 댓글 작성 처리
    @PostMapping("/comment/write")
    public String commentWrite(photoBoardCommentDto commentDto,
                               HttpSession session) {

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 댓글을 작성할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 댓글 작성자를 현재 로그인한 사용자의 회원 번호로 설정
        commentDto.setMemberNo(loginUser.getNo());

        // 댓글 저장 처리
        commentService.write(commentDto);

        // 댓글 작성 후 상세보기로 돌아가되 조회수는 증가하지 않게 countHit=false를 붙임
        return "redirect:/photoBoard/view?no="
                + commentDto.getBoardNo()
                + "&countHit=false";
    }

    // 댓글 삭제 처리
    @GetMapping("/comment/delete")
    public String commentDelete(@RequestParam int no,
                                @RequestParam int boardNo,
                                HttpSession session) {

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 댓글을 삭제할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 관리자는 모든 댓글을 삭제할 수 있음
        if (isAdmin(loginUser)) {
            photoBoardService.adminDeleteComment(no);
        }

        // 일반 사용자는 자기 댓글만 삭제할 수 있음
        else {
            commentService.delete(no, loginUser.getNo());
        }

        // 댓글 삭제 후 상세보기로 돌아가되 조회수는 증가하지 않게 countHit=false를 붙임
        return "redirect:/photoBoard/view?no="
                + boardNo
                + "&countHit=false";
    }

    // 게시글 신고 처리
    @PostMapping("/report/board")
    public String reportBoard(@RequestParam int boardNo,
                              @RequestParam String reason,
                              @RequestParam(required = false) String content,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 신고할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 신고 정보를 담을 DTO 생성
        ReportDto reportDto = new ReportDto();

        // 신고한 사람 번호 저장
        reportDto.setReporterNo(loginUser.getNo());

        // 신고 대상이 게시글임을 저장
        reportDto.setTargetType("BOARD");

        // 신고 대상 게시글 번호 저장
        reportDto.setTargetNo(boardNo);

        // 신고 사유 저장
        reportDto.setReason(reason);

        // 신고 상세 내용 저장
        reportDto.setContent(content);

        // 신고 등록 처리
        int result = reportService.insertReport(reportDto);

        // 이미 신고한 게시글이면 오류 메시지 저장
        if (result == -1) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "이미 신고한 게시글입니다."
            );
        }

        // 신고가 정상 등록되면 성공 메시지 저장
        else if (result > 0) {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "게시글 신고가 접수되었습니다."
            );
        }

        // 신고 후 상세보기로 돌아가되 조회수는 증가하지 않게 countHit=false를 붙임
        return "redirect:/photoBoard/view?no=" + boardNo + "&countHit=false";
    }

    // 댓글 신고 처리
    @PostMapping("/report/comment")
    public String reportComment(@RequestParam int boardNo,
                                @RequestParam int commentNo,
                                @RequestParam String reason,
                                @RequestParam(required = false) String content,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        // 세션에서 로그인한 사용자 정보를 꺼냄
        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        // 로그인하지 않은 사용자는 신고할 수 없으므로 로그인 페이지로 이동
        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 신고 정보를 담을 DTO 생성
        ReportDto reportDto = new ReportDto();

        // 신고한 사람 번호 저장
        reportDto.setReporterNo(loginUser.getNo());

        // 신고 대상이 댓글임을 저장
        reportDto.setTargetType("COMMENT");

        // 신고 대상 댓글 번호 저장
        reportDto.setTargetNo(commentNo);

        // 신고 사유 저장
        reportDto.setReason(reason);

        // 신고 상세 내용 저장
        reportDto.setContent(content);

        // 신고 등록 처리
        int result = reportService.insertReport(reportDto);

        // 이미 신고한 댓글이면 오류 메시지 저장
        if (result == -1) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "이미 신고한 댓글입니다."
            );
        }

        // 신고가 정상 등록되면 성공 메시지 저장
        else if (result > 0) {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "댓글 신고가 접수되었습니다."
            );
        }

        // 신고 후 상세보기로 돌아가되 조회수는 증가하지 않게 countHit=false를 붙임
        return "redirect:/photoBoard/view?no=" + boardNo + "&countHit=false";
    }
}