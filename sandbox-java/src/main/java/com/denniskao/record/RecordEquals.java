package com.denniskao.record;

import java.util.List;
import java.util.Objects;

public class RecordEquals {
    record Foos(List<String> foos) {
    }

    public static void main(String[] args) {

        var l1 = List.of("abc", "def");
        var l2 = List.of("abc", "def");
        var f1 = new Foos(l1);
        var f2 = new Foos(l2);
        System.out.println(Objects.equals(f1, f2));
        System.out.println(Objects.equals(l1, l2));

    }
}
