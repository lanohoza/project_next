package com.pajiniweb.oriachad_backend.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    T body;
    ResponseStatus status;
    String message;

    public static ResponseDto<Object> ok(Object body) {
        return ResponseDto.builder().status(ResponseStatus.ok).body(body).build();
    }

    public static ResponseDto<Object> ok(Object body, String message) {
        return ResponseDto.builder().status(ResponseStatus.ok).body(body).message(message).build();
    }

    public static ResponseDto<Object> error(String message) {
        return ResponseDto.builder().status(ResponseStatus.error).message(message).build();
    }

    public static ResponseDto<Object> error(Object body, String m) {
        return ResponseDto.builder().status(ResponseStatus.error).body(body).message(m).build();
    }

    public enum ResponseStatus {
        ok, error

    }

}

