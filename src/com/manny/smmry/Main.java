package com.manny.smmry;

public class Main {

    public static void main(String[] args) {
        try {
            App app = new App();
            app.setSize(700,700);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}