package shop.mtcoding.blog2.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog2.dto.board.BoardResp.BoardMainRespDto;

@MybatisTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findAllWithUser_test() throws Exception {
        // given
        ObjectMapper om = new ObjectMapper();

        // when
        List<BoardMainRespDto> boardMainRespDto = boardRepository.findAllWithUser();
        System.out.println("테스트 : size : " + boardMainRespDto.size());
        String responseBody = om.writeValueAsString(boardMainRespDto);
        System.out.println("테스트 : " + responseBody);

        // then
        assertThat(boardMainRespDto.get(5).getUsername()).isEqualTo("love");
    }
}
