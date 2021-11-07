package com.company;

public abstract class Entity{
    private final String name;
    public Entity(String name){
        this.name = name;
    }
    public Entity() {
        name = null;
    }
    public abstract String getName();
    public abstract void Story();
    public abstract void EndStory();
}
