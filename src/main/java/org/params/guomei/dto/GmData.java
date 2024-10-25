package org.params.guomei.dto;

public class GmData<T>{
    // 【重要：】1. 根据接口文档规范约定，如果是正常的status ="000000"
    //         2. 如果是异常情况 status ="000001" message 按照实际情况给出
    private String status ="000000";
    private T data;
    private String message ="成功";

    public GmData() {
    }

    public GmData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
