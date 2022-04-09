package com.company.sourse;

import java.io.Serializable;

public class DragonHead implements Serializable {
    private long size;

    /**
     * Set size
     * @param size size
     */
    public DragonHead(Long size){
        this.size = size;
    }

    /**
     * Method to get size
     * @return size
     */
    public long getSize(){
        return size;
    }
    @Override
    public String toString(){
        return ""+size;
    }
}
