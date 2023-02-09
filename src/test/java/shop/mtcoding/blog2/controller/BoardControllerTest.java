package shop.mtcoding.blog2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog2.dto.board.BaordReq.BoardSaveReqDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog2.model.User;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;

    @Autowired
    private ObjectMapper om;

    @BeforeEach // Test메서드 실행 직전마다 호출된다
    public void setUp() {
        // 임시 세션 생성하기
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void save_test() throws Exception {
        // given
        String title = "";
        for (int i = 0; i < 100; i++) {
            title += "가";
        }

        BoardSaveReqDto boardSaveReqDto = new BoardSaveReqDto();
        boardSaveReqDto.setTitle(title);
        boardSaveReqDto.setContent("수정된내용");

        String requestBody = om.writeValueAsString(boardSaveReqDto);

        // when
        ResultActions resultActions = mvc.perform(
                post("/board")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession)); // session이 주입된 채로 요청

        // then
        // resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(jsonPath("$.msg").value("게시글 작성 성공"));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void datail_test() throws Exception {
        // given
        int id = 4;

        // when
        ResultActions resultActions = mvc.perform(
                get("/board/" + id));

        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        BoardDetailRespDto dto = (BoardDetailRespDto) map.get("dto");

        String model = om.writeValueAsString(dto);
        System.out.println("테스트 : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dto.getId()).isEqualTo(4);
        assertThat(dto.getUsername()).isEqualTo("love");
        assertThat(dto.getTitle()).isEqualTo("4번째 제목");
        assertThat(dto.getContent()).isEqualTo("4번째 내용");
        assertThat(dto.getUserId()).isEqualTo(2);
    }

    @Test
    public void delete_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                delete("/board/" + id).session(mockSession)); // session이 주입된 채로 요청
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.print("테스트: " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.msg").value("삭제성공"));
        resultActions.andExpect(status().isOk());
    }
}