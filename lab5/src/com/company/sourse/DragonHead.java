package com.company.sourse;

public class DragonHead {
    private long size;
    public DragonHead(Long size){
        this.size = size;
    }
    public long getSize(){
        return size;
    }
    @Override
    public String toString(){
        return ""+size;
    }
}
