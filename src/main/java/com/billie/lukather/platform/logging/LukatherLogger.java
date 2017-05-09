package com.billie.lukather.platform.logging;

import org.springframework.boot.logging.LogLevel;

/**
 * Created by matthew on 5/9/17.
 */
public interface LukatherLogger {

    boolean isLogLevel(LogLevel loglevel, Class<?> clazz);
    void log(LogLevel logLevel, Class<?> clazz, String message);
    void log(LogLevel logLevel, Class<?> clazz, String message, Throwable throwable);

}
