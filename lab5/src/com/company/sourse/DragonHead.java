package com.company.sourse;

public class DragonHead {
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
