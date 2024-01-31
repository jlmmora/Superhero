package com.plexus.w2m.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
//La anotación @Component se incluye para que Spring detecte el Bean.
// LogExecutionTimeAspect es la clase donde implementaremos la lógica que queremos que inyecte la anotación personalizada.
public class LogExecutionTimeAspect {

    @Around("@annotation(com.plexus.w2m.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();

        final Object proceed = joinPoint.proceed();

        final long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }

}