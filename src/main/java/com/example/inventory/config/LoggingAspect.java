// src/main/java/com/example/inventory/config/LoggingAspect.java
package com.example.inventory.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(1)
public class LoggingAspect {

    // Controllers + Services + Repositories packages
    @Around("execution(* com.example.inventory.web..*(..)) || " +
            "execution(* com.example.inventory.service..*(..)) || " +
            "execution(* com.example.inventory.repository..*(..))")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        String sig = pjp.getSignature().toShortString();
        log.info("➡️ ENTER {}", sig);
        long t0 = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();
            long dt = System.currentTimeMillis() - t0;
            log.info("✅ EXIT  {} ({} ms)", sig, dt);
            return result;
        } catch (Throwable ex) {
            long dt = System.currentTimeMillis() - t0;
            log.error("💥 ERROR {} ({} ms): {}", sig, dt, ex.getMessage(), ex);
            throw ex;
        }
    }
}
