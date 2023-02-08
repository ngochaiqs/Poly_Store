package com.poly_store.model.EventBus;

import com.poly_store.model.NhaCungCap;

public class SuaXoaEventNCC {
    NhaCungCap nhaCungCap;

    public SuaXoaEventNCC(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }
}
