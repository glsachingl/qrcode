package com.infy.qrcode.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class AspectPointCut {

    @Pointcut("execution(* com.sachin.qrcode.controller..*(..))")
    public void logControllerMethodCalls(){

    }

    @Pointcut("execution(* com.sachin.qrcode.service..*(..))")
    public void logServiceMethodCalls(){

    }

}
