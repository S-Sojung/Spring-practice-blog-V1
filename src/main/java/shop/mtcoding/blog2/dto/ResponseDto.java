package shop.mtcoding.blog2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDto<T> {

    private int code;
    private String msg;
    private T data;
}
