package com.admin.common.exception;

/**
 * 自定义异常
 */
public class BDException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public BDException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BDException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BDException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BDException(int code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
