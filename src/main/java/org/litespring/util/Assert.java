package org.litespring.util;

public class Assert {
    public static void notNull(String message, Object object) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
