package com.gif;

import java.time.LocalDateTime;

public final class Logger {

    public static void log(String text) {
        System.out.println(LocalDateTime.now() + " " + text);
    }
}
