package com.bilibili.domain.exception;


public class ConditionException extends RuntimeException{
    public static final long serialVersionID=1L;

    private String code;

    public ConditionException(String code,String name){
        super(name);
        this.code=code;
    }

    public ConditionException(String name){
        super(name);
        code="500";//通用的错误状态码为500
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
