package com.example.trinhngocquang_qlbaihat;

public class BaiHat {
    public int idBH;
    public String TenBH;
    public String TenCS;
    public byte[] Anh;

    public BaiHat(int idBH, String tenBH, String tenCS, byte[] anh) {
        this.idBH = idBH;
        TenBH = tenBH;
        TenCS = tenCS;
        Anh = anh;
    }
}
