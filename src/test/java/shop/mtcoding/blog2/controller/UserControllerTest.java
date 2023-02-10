package shop.mtcoding.blog2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog2.model.User;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void join_test() throws Exception {
        System.out.print("테스트 : join_test()");
        // given
        String requestBody = "username=cos&password=1234&email=ssar@nate.com";

        // when
        ResultActions resultActions = mvc.perform(post("/join").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void login_test() throws Exception {
        System.out.print("테스트 : join_test()");
        // given
        String requestBody = "username=ssar&password=1234";

        // when
        ResultActions resultActions = mvc.perform(post("/login").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        HttpSession session = resultActions.andReturn().getRequest().getSession();
        User pricipal = (User) session.getAttribute("principal");
        // System.out.println(pricipal.getUsername());

        // then
        assertThat(pricipal.getUsername()).isEqualTo("ssar");
        resultActions.andExpect(status().is3xxRedirection());
    }
}
