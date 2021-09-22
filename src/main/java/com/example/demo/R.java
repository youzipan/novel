package com.example.demo;

public class R {

    public static R ok(Object value) {
        R r = new R();
        r.data = value;
        return r;
    }

    public static R ok(Object value, long count) {
        R r = new R();
        r.data = value;
        r.count = count;
        return r;
    }

    public static R fail(int code) {
        R r = new R();
        r.code = code;
        return r;
    }

    private int code = 0;
    private String msg;
    private long count;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
