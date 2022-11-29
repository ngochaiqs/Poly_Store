package com.poly_store.model;

import java.util.List;

public class NhaCungCapModel {
    boolean success;
    String message;
    List<NhaCungCap> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NhaCungCap> getResult() {
        return result;
    }

    public void setResult(List<NhaCungCap> result) {
        this.result = result;
    }
}
