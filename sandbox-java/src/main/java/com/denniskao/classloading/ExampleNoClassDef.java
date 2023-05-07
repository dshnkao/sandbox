package com.denniskao.classloading;

public class ExampleNoClassDef {
    public static class BadInit {
        private static int thisIsFine = 1 / 0;
    }
    public static void main(String[] args) {
        try {
            // loads BadInit class
            var init = new BadInit();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // NoClassDefFoundError -- since the first load failed (but class was found)
        var init2 = new BadInit();
        System.out.println(init2.thisIsFine);
    }
}
