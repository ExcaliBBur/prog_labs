package com.company.sourse;

public enum Color {
    RED,
    BLACK,
    ORANGE,
    BROWN;
    public static String list(){
        String list = "";
        for (Color n : Color.values()) list += n + ", ";
        return list.substring(0, list.length()-2);
    }
}
