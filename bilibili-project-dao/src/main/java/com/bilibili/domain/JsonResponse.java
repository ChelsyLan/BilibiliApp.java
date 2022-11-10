package com.bilibili.domain;

public class JsonResponse<T> {
    private String code;

    private String msg;

    private T data;//T 对应泛型，因为返回的类型是多样的

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data) {
        this.data = data;
        msg = "success";
        code = "0";//初始化为成功信息
    }

    public static JsonResponse<String> success() {
        return new JsonResponse<String>(null);//请求成功，不返回具体信息

    }

    public static JsonResponse<String> success(String data) {
        return new JsonResponse<>((data));//请求成功，返回令牌
    }

    public static JsonResponse<String> fail() {
        return new JsonResponse<>("1", "fail");
    }

    public static JsonResponse<String> fail(String code, String msg) {
        return new JsonResponse<>(code, msg);//返回定制化信息
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
