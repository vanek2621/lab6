package com.lab6.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.lab6.controller..*(..))")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("=== Method Invocation ===");
        logger.info("Class: {}", className);
        logger.info("Method: {}", methodName);
        logger.info("Input Parameters: {}", formatArguments(args));

        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable exception = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            exception = e;
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("=== Method Completion ===");
            logger.info("Class: {}", className);
            logger.info("Method: {}", methodName);
            logger.info("Execution Time: {} ms", executionTime);

            if (exception != null) {
                logger.error("Exception occurred: {}", exception.getClass().getSimpleName());
                logger.error("Exception message: {}", exception.getMessage());
            } else {
                logger.info("Return Value: {}", formatReturnValue(result));
            }
            logger.info("=========================");
        }
    }

    private String formatArguments(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }
        return Arrays.toString(
                Arrays.stream(args)
                        .map(this::formatArgument)
                        .toArray()
        );
    }

    private String formatArgument(Object arg) {
        if (arg == null) {
            return "null";
        }
        if (arg instanceof String || arg instanceof Number || arg instanceof Boolean) {
            return arg.toString();
        }
        if (arg.getClass().isArray()) {
            return Arrays.toString((Object[]) arg);
        }
        return arg.getClass().getSimpleName() + "@" + Integer.toHexString(arg.hashCode());
    }

    private String formatReturnValue(Object result) {
        if (result == null) {
            return "null";
        }
        if (result instanceof Iterable) {
            Iterable<?> iterable = (Iterable<?>) result;
            long count = 0;
            for (Object ignored : iterable) {
                count++;
            }
            return result.getClass().getSimpleName() + " (size: " + count + ")";
        }
        if (result instanceof String || result instanceof Number || result instanceof Boolean) {
            return result.toString();
        }
        return result.getClass().getSimpleName();
    }
}

