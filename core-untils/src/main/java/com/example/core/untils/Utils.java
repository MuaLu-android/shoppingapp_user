package com.example.core.untils;


import com.example.core.model.GioHang;
import com.example.core.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "https://laulu.io.vn/webbanhang/banhang/";
    public static List<GioHang> manggiohang;
    public static String tokenSend;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
}
