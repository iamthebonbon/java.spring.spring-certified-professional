package com.example.scp;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* *..*Controller.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            logger.info("Around Before: {}", proceedingJoinPoint.getTarget().getClass());
            Object proceed = proceedingJoinPoint.proceed();
            logger.info("Around After: {}", proceed);
            return proceed;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Before("execution(* *..*Controller.*(..))")
    public void before(JoinPoint joinPoint) {
        logger.info("Before: {}", joinPoint.getArgs());
    }

    @After("execution(* *..*Controller.*(..))")
    public void after(JoinPoint joinPoint) {
        logger.info("After: {}", joinPoint.getArgs());
    }

    @AfterReturning(value = "execution(* *..*Controller.*(..))", returning = "r")
    public void afterReturning(JoinPoint joinPoint, Object r) {
        logger.info("AfterReturning: {}. Return: {}", joinPoint.getArgs(), r);
    }

    @AfterThrowing(value = "execution(* *..*Controller.*(..))", throwing = "r")
    public void AfterThrowing(JoinPoint joinPoint, Exception r) {
        logger.info("AfterThrowing: {}. Exception: {}", joinPoint.getArgs(), r);
    }
}
