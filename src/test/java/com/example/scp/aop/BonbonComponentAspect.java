package com.example.scp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BonbonComponentAspect {
    private final Logger logger = LoggerFactory.getLogger(BonbonComponentAspect.class);

    @Around("bean(bonbonComponent)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            logger.info("BonbonComponentAspect: Aspect@Around Before: {}", proceedingJoinPoint.getTarget().getClass());
            Object proceed = proceedingJoinPoint.proceed();
            logger.info("BonbonComponentAspect: Aspect@Around After: {}", proceed);
            return proceed;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
