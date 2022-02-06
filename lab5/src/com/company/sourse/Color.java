package com.company.sourse;

import com.company.utilities.DataController;

public enum Color {
    RED(1),
    BLACK(2),
    ORANGE(3),
    BROWN(4);

    public final int value;
    private Color(int value){
        this.value = value;
    }
}
