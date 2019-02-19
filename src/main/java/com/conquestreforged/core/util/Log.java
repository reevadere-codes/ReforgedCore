package com.conquestreforged.core.util;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static final int WIDEST_NAME = "conquest:this_is_a_long_name_used_for_padding".length();
    private static final Logger logger = LogManager.getLogger("ReforgedCore");

    public static final int ERROR = 0;
    public static final int WARN = 1;
    public static final int INFO = 2;
    public static final int DEBUG = 3;

    private static int level = Log.DEBUG;

    private Log() {}

    public static void level(int level) {
        if (level >= ERROR && level <= DEBUG) {
            Log.level = level;
        }
    }

    public static void info(String message, Object... args) {
        if (Log.level < Log.INFO) {
            return;
        }
        Log.logger.info(message, args);
    }

    public static void error(String message, Object... args) {
        if (Log.level < Log.ERROR) {
            return;
        }
        Log.logger.error(message, args);
    }

    public static void warn(String message, Object... args) {
        if (Log.level < Log.WARN) {
            return;
        }
        Log.logger.warn(message, args);
    }

    public static void debug(String message, Object... args) {
        if (Log.level < Log.DEBUG) {
            return;
        }
        Log.logger.debug(message, args);
    }

    public static void register(String type, ResourceLocation regName, Object object) {
        if (Log.level < Log.DEBUG) {
            return;
        }
        String name = regName.toString();
        String padding = Log.getPadding(name);
        String className = object.getClass().getName();
        logger.debug("Registered {} => Name: {}{} Type: {}", type, name, padding, className);
    }

    private static String getPadding(String name) {
        StringBuilder sb = new StringBuilder(WIDEST_NAME - name.length());
        for (int i = name.length(); i < WIDEST_NAME; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    private static class DebugLogger {

        private final String name;
        private final DateFormat time = new SimpleDateFormat("HH:mm:ss");

        private DebugLogger(String name) {
            this.name = name;
        }

        public void info(String message, Object... args) {
            log("INFO", message, args);
        }

        public void error(String message, Object... args) {
            log("ERROR", message, args);
        }

        public void warn(String message, Object... args) {
            log("WARN", message, args);
        }

        public void debug(String message, Object... args) {
            log("DEBUG", message, args);
        }

        private void log(String prefix, String message, Object... args) {
            System.out.print("[");
            System.out.print(time.format(new Date()));
            System.out.print("] ");
            // value
            System.out.print("[");
            System.out.print(name);
            System.out.print("/");
            // prefix
            System.out.print(prefix);
            System.out.print("]: ");
            // message
            System.out.println(MessageFormat.format(message, args));
        }
    }
}
