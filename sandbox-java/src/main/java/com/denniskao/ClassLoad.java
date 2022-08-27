package com.denniskao;

public class ClassLoad {
    public static void main(String[] args) {
        var classLoader = ClassLoad.class.getClassLoader();
        System.out.println(classLoader);
    }
}
