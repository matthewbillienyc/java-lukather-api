package com.billie.lukather.platform.logging;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by matthew on 5/9/17.
 */
@Aspect
@Component
public class LoggingAspect {

    private static String ENTERING = "[ Entering %s ]";
    private static String ENTERING_WITH_ARGS = "[ Entering %s with arguments %s ]";
    private static String LEAVING = "[ Leaving  %s after %s ms ] ";
    private static String AFTER_THROWING = "[ Exception thrown in %s: %s with params %s ]";


    @Pointcut("within(com.billie.lukather..resource..*)")
    public void resourceClasses() {
    }

    @Autowired
    private LukatherLogger logger;

    @Around(value = "@annotation(loggable)")
    public Object logAround(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        long start = System.currentTimeMillis();
        Class<? extends Object> clazz = joinPoint.getTarget().getClass();
        String name = joinPoint.getSignature().getName();
        if (ArrayUtils.isEmpty(joinPoint.getArgs())) {
            logger.log(loggable.value(), clazz, String.format(ENTERING, name));
        } else {
            logger.log(loggable.value(), clazz, String.format(ENTERING_WITH_ARGS, name, Arrays.toString(joinPoint.getArgs())));
        }

        Object result = joinPoint.proceed();
        logger.log(loggable.value(), clazz, String.format(LEAVING, name, (System.currentTimeMillis() - start)));

        return result;
    }


    @Around("(resourceClasses() || serviceClasses())")
    public Object logAroundPackages(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Class<? extends Object> clazz = joinPoint.getTarget().getClass();
        String name = joinPoint.getSignature().getName();
        if (ArrayUtils.isEmpty(joinPoint.getArgs())) {
            logger.log(LogLevel.INFO, clazz, String.format(ENTERING, name));
        } else {
            logger.log(LogLevel.INFO, clazz, String.format(ENTERING_WITH_ARGS, name, stringify(joinPoint.getArgs())));
        }

        Object result = joinPoint.proceed();
        logger.log(LogLevel.INFO, clazz, String.format(LEAVING, name, (System.currentTimeMillis() - start)));

        return result;
    }

    @AfterThrowing(value = "(resourceClasses() || serviceClasses())",
            throwing = "throwable", argNames = "joinPoint, throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {

        Class<? extends Object> clazz = joinPoint.getTarget().getClass();
        String name = joinPoint.getSignature().getName();
        logger.log(LogLevel.ERROR, clazz, String.format(AFTER_THROWING, name, throwable.getMessage(),
                Arrays.toString(joinPoint.getArgs())),
                throwable);
    }

    private String stringify(Object[] objects) {
        Assert.notNull(objects, "Cannot stringify empty array!");
        String arguments = Arrays.stream(objects).filter(s -> s != null && !StringUtils.isEmpty(s.toString())).collect(Collectors.toList()).toString();
        return arguments;
    }

}
