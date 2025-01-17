package com.example.gc_coffee.global.util;

public class Ut {
    public static class str {
        public static boolean isBlank (String str) {
            return str == null || str.trim().isEmpty();
            // trim: 문자열의 앞과 뒤에 있는 공백을 제거
            //  ㄴ 사용자의 실수로 발생한 공백도 빈 문자열 처리하여 유효성 검사를 수행하기 위함
            //  ㄴ 공백도 빈 문자열로 처리하기 위함
            // isEmpty(): 빈 문자열 인지를 확인 (!= null)
        }
    }
}