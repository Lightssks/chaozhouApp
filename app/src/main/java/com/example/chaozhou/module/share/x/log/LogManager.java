package com.example.chaozhou.module.share.x.log;
public final class LogManager {
    private static Logger logger = new LoggerDefault();

    public LogManager() {
    }

    public static void setLogger(Logger newLogger) {
        logger = newLogger;
    }

    public static Logger getLogger() {
        return logger;
    }
}

