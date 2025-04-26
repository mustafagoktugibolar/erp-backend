package com.archproj.erp_backend.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogHelper {

    private static final Logger logger = LoggerFactory.getLogger("Logger initialized!");

    private LogHelper() {}

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }
}