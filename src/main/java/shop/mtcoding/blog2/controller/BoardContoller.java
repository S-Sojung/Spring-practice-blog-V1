package shop.mtcoding.blog2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog2.dto.ResponseDto;
import shop.mtcoding.blog2.dto.board.BaordReq.BoardSaveReqDto;
import shop.mtcoding.blog2.handler.ex.CustomApiException;
import shop.mtcoding.blog2.model.BoardRepository;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.BoardService;

@Controller
public class BoardContoller {

    @Autowired
    HttpSession session;
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

    @GetMapping({ "/", "/board" })
    public String main(Model model) {

        model.addAttribute("dtos", boardRepository.findAllWithUser());
        return "board/main";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id) {
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board")
    public @ResponseBody ResponseEntity<?> save(@RequestBody BoardSaveReqDto boardSaveReqDto) {

        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다.", HttpStatus.UNAUTHORIZED); // 401
        }
        if (boardSaveReqDto.getTitle() == null || boardSaveReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title 작성해주세요");
        }
        if (boardSaveReqDto.getContent() == null || boardSaveReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content 작성해주세요");
        }
        if (boardSaveReqDto.getTitle().length() > 100) {
            throw new CustomApiException("title의 길이가 100자 이하여야 합니다");
        }

        boardService.글쓰기(boardSaveReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 작성 성공", null), HttpStatus.CREATED);
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id) {
        return "board/updateForm";
    }
}
