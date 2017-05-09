package com.billie.lukather.platform.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

/**
 * Created by matthew on 5/9/17.
 */
@Component
public class LukatherLoggerImpl implements LukatherLogger {
    @Override
    public boolean isLogLevel(LogLevel loglevel, Class<?> clazz) {
        switch(loglevel) {
            case DEBUG: return logger(clazz).isDebugEnabled();
            case ERROR: return logger(clazz).isErrorEnabled();
            case INFO: return logger(clazz).isInfoEnabled();
            case TRACE: return logger(clazz).isTraceEnabled();
            case WARN: return logger(clazz).isWarnEnabled();
            default: return false;
        }
    }

    @Override
    public void log(LogLevel logLevel, Class<?> clazz, String message) {
        switch (logLevel) {
            case DEBUG:
                debug(clazz, message);
                break;
            case ERROR:
                error(clazz, message);
                break;
            case INFO:
                info(clazz, message);
                break;
            case TRACE:
                trace(clazz, message);
                break;
            case WARN:
                warn(clazz, message);
                break;
        }
    }

    @Override
    public void log(LogLevel logLevel, Class<?> clazz, String message, Throwable throwable) {
        switch (logLevel) {
            case DEBUG:
                debug(clazz, message, throwable);
                break;
            case ERROR:
                error(clazz, message, throwable);
                break;
            case INFO:
                info(clazz, message, throwable);
                break;
            case TRACE:
                trace(clazz, message, throwable);
                break;
            case WARN:
                warn(clazz, message, throwable);
                break;
        }
    }

    private void debug(Class<?> clazz, String message) {
        logger(clazz).debug(message);
    }
    private void debug(Class<?> clazz, String message, Throwable throwable) {
        logger(clazz).debug(message, throwable);
    }

    private void error(Class<?> clazz, String message) {
        logger(clazz).error(message);
    }
    private void error(Class<?> clazz, String message, Throwable throwable) {
        logger(clazz).error(message, throwable);
    }

    private void info(Class<?> clazz, String message) {
        logger(clazz).info(message);
    }
    private void info(Class<?> clazz, String message, Throwable throwable) {
        logger(clazz).error(message, throwable);
    }

    private void trace(Class<?> clazz, String message) {
        logger(clazz).trace(message);
    }
    private void trace(Class<?> clazz, String message, Throwable throwable) {
        logger(clazz).error(message, throwable);
    }

    private void warn(Class<?> clazz, String message) {
        logger(clazz).error(message);
    }
    private void warn(Class<?> clazz, String message, Throwable throwable) {
        logger(clazz).warn(message, throwable);
    }
    
    private Logger logger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
