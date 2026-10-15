package com.payamsdev.position;


public class Location {

    private final String name;
    private final Camera cam;

    public Location(String name, Camera cam) {
        this.name = name;
        this.cam = cam;
    }
    
    public Location(String name) {
    	this(name, null); 
    }

    public String getName() {
        return name;
    }

    public Camera getCamera() {
        return cam;
    }

    @Override
    public String toString() {
        return name + " (" + cam + ")";
    }
}