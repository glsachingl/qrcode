package com.infy.qrcode.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    @Around("com.sachin.qrcode.aspect.AspectPointCut.logControllerMethodCalls()")
    public Object controllerLogger(ProceedingJoinPoint jp) throws Throwable{
        String method = jp.getSignature().getName();
        String className = jp.getTarget().getClass().getName();
        String argument = Arrays.toString(jp.getArgs());

        log.info(" Calling Method {} in class {} with arguments {}", method, className, argument);

        try {
            final long startTime=System.currentTimeMillis();
            Object result = jp.proceed();
            final long endTime=System.currentTimeMillis();
            log.info(" Completed in {} ms for Method {} in class {} with arguments {}",(endTime-startTime), method, className, argument);
            return result;
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            log.error("Exception",e);
            throw e;
        }
    }

    @Around("com.sachin.qrcode.aspect.AspectPointCut.logServiceMethodCalls()")
    public Object serviceLogger(ProceedingJoinPoint jp) throws Throwable{
        String method = jp.getSignature().getName();
        String className = jp.getTarget().getClass().getName();
        String argument = Arrays.toString(jp.getArgs());

        log.info(" Calling Method {} in class {} with arguments {}", method, className, argument);

        try {
            final long startTime=System.currentTimeMillis();
            Object result = jp.proceed();
            final long endTime=System.currentTimeMillis();
            log.info(" Completed in {} ms for Method {} in class {} with arguments {}",(endTime-startTime), method, className, argument);
            return result;
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            log.error("Exception",e);
            throw e;
        }
    }
}