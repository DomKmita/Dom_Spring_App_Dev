package com.example.lab10.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RepositoryAspect {
    @Pointcut("execution(* com.example.lab10.repositories.*.*(..))")
    public void RepositoryMethods() {}

    @Before("RepositoryMethods()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info(">> Entering Method: {} with arguments = {}",
                signature.getMethod().getName(),
                joinPoint.getArgs());
    }

    @After("RepositoryMethods()")
    public void logAfter(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info("<< Exiting Method: {}", signature.getMethod().getName());
    }

    @AfterReturning(pointcut = "RepositoryMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info("<< Method Returned: {} with result = {}",
                signature.getMethod().getName(),
                result);
    }

    @AfterThrowing(pointcut = "RepositoryMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.error("!! Method Threw Exception: {} with message = {}",
                signature.getMethod().getName(),
                error.getMessage());
    }

    @Around("RepositoryMethods()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        log.info(">> [Around] Entering Method: {}", methodName);
        long startTime = System.currentTimeMillis();

        try {
            Object result = proceedingJoinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            log.info("<< [Around] Method: {} executed in {} ms", methodName, timeTaken);
            return result;
        } catch (Throwable throwable) {
            long timeTaken = System.currentTimeMillis() - startTime;
            log.error("!! [Around] Method: {} threw exception after {} ms", methodName, timeTaken);
            throw throwable;
        }
    }
}
