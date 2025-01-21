package com.example.gc_coffee.global.exceptions.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    OK(HttpStatus.OK, "정상 처리 되었습니다."),

    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없습니다."),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    NOT_FOUND_CART(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),

    DUPLICATED_EMAIL(HttpStatus.CREATED, "이미 존재하는 이메일입니다."),
    DUPLICATED_ITEM(HttpStatus.CREATED, "이미 존재하는 상품입니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "정상적인 요청이 아닙니다."),
    NO_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "알수없는 에러가 발생했습니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
