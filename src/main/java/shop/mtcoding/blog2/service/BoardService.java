package shop.mtcoding.blog2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog2.dto.board.BaordReq.BoardSaveReqDto;
import shop.mtcoding.blog2.dto.board.BaordReq.BoardUpdateReqDto;
import shop.mtcoding.blog2.handler.ex.CustomApiException;
import shop.mtcoding.blog2.model.Board;
import shop.mtcoding.blog2.model.BoardRepository;

@Transactional(readOnly = true) // 여기 붙이면 모든 메서드에 다 붙음
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId) {

        int result = boardRepository.insert(boardSaveReqDto.getTitle(), boardSaveReqDto.getContent(),
                userId);

        if (result != 1) {
            throw new CustomApiException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void 게시글삭제(int id, int principalId) {
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new CustomApiException("없는 게시글을 삭제할 수 없습니다");
        }
        if (principalId != board.getUserId()) {
            throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버가 일시적인 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void 게시글수정(int id, BoardUpdateReqDto boardUpdateReqDto, int principalId) {
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomApiException("해당 게시글을 찾을 수 없습니다."); // bad request
        }
        if (boardPS.getUserId() != principalId) {
            throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        try {
            boardRepository.updateById(id, boardUpdateReqDto.getTitle(), boardUpdateReqDto.getContent());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomApiException("게시글 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
