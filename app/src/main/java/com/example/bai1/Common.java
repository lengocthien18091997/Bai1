package com.example.bai1;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class Common {

    public static String validateAddActivity(String name, String year, String address) {
        String err = "ok";
        if (name.isEmpty() || year.isEmpty() || address.isEmpty()) {
            err = "Nhập thiếu hạng mục!";
        } else if (!isNumeric(year)){
            err = "Nhập sai định dạng năm sinh!";
        }
        return err;
    }

    public static boolean isNumeric(String year) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(year, pos);
        return year.length() == pos.getIndex();
    }
}
