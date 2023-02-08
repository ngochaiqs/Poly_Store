package com.poly_store.model;

import java.io.Serializable;

public class NhaCungCap implements Serializable {
    int maNCC;
    String tenNCC;
    String diaChiNCC;
    String SDTNCC;

    public NhaCungCap() {
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiaChiNCC() {
        return diaChiNCC;
    }

    public void setDiaChiNCC(String diaChiNCC) {
        this.diaChiNCC = diaChiNCC;
    }

    public String getSDTNCC() {
        return SDTNCC;
    }

    public void setSDTNCC(String SDTNCC) {
        this.SDTNCC = SDTNCC;
    }
}
