package shop.mtcoding.blog2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog2.dto.board.BaordReq.BoardSaveReqDto;
import shop.mtcoding.blog2.handler.ex.CustomApiException;
import shop.mtcoding.blog2.model.BoardRepository;

@Transactional(readOnly = true) // 여기 붙이면 모든 메서드에 다 붙음
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId) {

        int result = boardRepository.insert(boardSaveReqDto.getTitle(), boardSaveReqDto.getContent(),
                userId);

        if (result != 1) {
            throw new CustomApiException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
