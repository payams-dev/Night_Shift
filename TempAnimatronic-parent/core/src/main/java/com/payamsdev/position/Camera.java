package com.payamsdev.position;



public enum Camera {
    CAM_1A("1A"),
    CAM_1B("1B"),
    CAM_1C("1C"),
    CAM_2A("2A"),
    CAM_2B("2B"),
    CAM_3("3"),
    CAM_4A("4A"),
    CAM_4B("4B"),
    CAM_5("5"),
    CAM_6("6"),
    CAM_7("7");

    private final String code;

    Camera(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

   

    @Override
    public String toString() {
        return "Camera " + code;
    }
}