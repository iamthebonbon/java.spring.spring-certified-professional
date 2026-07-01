package com.example.scp.aop;

import com.example.scp.aop.annotation.BonbonAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BonbonAnnotationAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(BonbonAnnotationAspect.class);

    @Before("@annotation(bonbonAnnotation)")
    public void bindAnnotation(BonbonAnnotation bonbonAnnotation) {
        String requiredRole = bonbonAnnotation.value();
        LOGGER.info("bindAnnotation: {}", requiredRole);
    }

    @Before("bean(bonbonService)")
    public void bonbonService(JoinPoint joinPoint) {
        LOGGER.info("bean(bonbonService): {}", System.currentTimeMillis());
    }

    @Before("@annotation(com.example.scp.aop.annotation.BonbonAnnotation)")
    public void referenceTypeAnnotation(JoinPoint jp) {
        LOGGER.info("referenceTypeAnnotation: {}", System.currentTimeMillis());
    }

    @Before("within(com.example.scp.service.BonbonServiceImpl)")
    public void within(JoinPoint jp) {
        LOGGER.info("within: {}", System.currentTimeMillis());
    }

    @Before("target(com.example.scp.service.interfaces.BonbonService)")
    public void target(JoinPoint jp) {
        LOGGER.info("target: {}", System.currentTimeMillis());
    }

    @Before("execution(* *..*Bonbon*.*(..)) && @within(org.springframework.stereotype.Service)")
    public void withinAnnotation(JoinPoint jp) {
        LOGGER.info("withinAnnotation: {}", System.currentTimeMillis());
    }

    @Before("execution(* *..*Bonbon*.*(..)) && @target(org.springframework.stereotype.Service)")
    public void targetAnnotation(JoinPoint jp) {
        LOGGER.info("targetAnnotation: {}", System.currentTimeMillis());
    }

    @Before("execution(* *..*Bonbon*.*(..)) && @annotation(com.example.scp.aop.annotation.BonbonAnnotation)")
    public void generalPointcutAndreferenceTypeAnnotation(JoinPoint jp) {
        LOGGER.info("generalPointcutAndreferenceTypeAnnotation: {}", System.currentTimeMillis());
    }

}
