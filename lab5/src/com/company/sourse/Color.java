package com.company.sourse;


public enum Color {
    RED(1),
    BLACK(2),
    ORANGE(3),
    BROWN(4);

    public final int value;

    /**
     * Get color value
     * @param value value
     */
    private Color(int value){
        this.value = value;
    }
}
