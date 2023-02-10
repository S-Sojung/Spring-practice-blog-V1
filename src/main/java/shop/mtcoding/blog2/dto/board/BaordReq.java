package shop.mtcoding.blog2.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BaordReq {

    @Getter
    @Setter
    public static class BoardSaveReqDto {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class BoardUpdateReqDto {
        private String title;
        private String content;
    }
}
