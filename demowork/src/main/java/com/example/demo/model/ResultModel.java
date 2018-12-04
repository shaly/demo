package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by sxmzv on 2018/11/26.
 */
@Data
public class ResultModel<T> implements Serializable {

    private int status;

    private String msg;

    private String errCode;

    private T data;

    public static final int SUCCESS=1;

    public static final int FAILED=1;

    public ResultModel(){
    }

    public ResultModel(int status,String msg){
        this.status=status;
        this.msg=msg;
    }

    public ResultModel(T data,int status){
        this.data=data;
        this.status=status;
    }

}
