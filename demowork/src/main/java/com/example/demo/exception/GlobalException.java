package com.example.demo.exception;

import lombok.*;

/**
 * Created by sxmzv on 2018/11/28.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class GlobalException extends RuntimeException {

    private String errCode;

    private String msg;

    private static String ERR_PREFIX="GLOBAL_";

    public GlobalException(String errCode, String msg){
        this.errCode = ERR_PREFIX + errCode;
        this.msg = msg;
    }
}
