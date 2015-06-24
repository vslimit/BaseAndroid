package com.vslimit.baseandroid.app.model;

/**
 * Created by vslimit on 15/4/4.
 */
public class Result<T> {
    private int error_code;
    private String tip;
    private T data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
