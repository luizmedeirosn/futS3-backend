package com.luizmedeirosn.futs3.utils;

import java.io.IOException;

public class ClearTerminal {

    private ClearTerminal() {
    }

    private static final String OS_NAME = System.getProperties().getProperty("os.name");
    private static ProcessBuilder process;

    public static void run() {
        if (process == null) {
            if (OS_NAME.equals("Linux")) {
                process = new ProcessBuilder("bash", "-c", "clear").inheritIO(); // Linux

            } else if (OS_NAME.equals("Windows")) {
                process = new ProcessBuilder("cmd", "/c", "cls").inheritIO(); // Windows

            } else {
                process = new ProcessBuilder("bash", "-c", "printf '\033c'").inheritIO(); // macOS
            }
        }
        clear();
    }

    private static void clear() {
        try {
            process.start().waitFor();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
