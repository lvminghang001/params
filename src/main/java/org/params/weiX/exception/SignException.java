package org.params.weiX.exception;

public class SignException extends RuntimeException{
    private static final long serialVersionUID = -7249463827733196662L;

    private Integer code;

    private String message;

    public SignException(String message) {
        this.message = message;
    }

    public SignException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public SignException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
