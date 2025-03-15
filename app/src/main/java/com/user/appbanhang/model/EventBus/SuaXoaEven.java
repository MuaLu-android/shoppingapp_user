package com.user.appbanhang.model.EventBus;

import com.user.appbanhang.model.SanPhamMoi;

public class SuaXoaEven {
    SanPhamMoi sanPhamMoi;

    public SuaXoaEven(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }

    public SanPhamMoi getSanPhamMoi() {
        return sanPhamMoi;
    }

    public void setSanPhamMoi(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }
}

