// src/main/java/com/example/inventory/config/RequestLoggingFilter.java
package com.example.inventory.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String rid = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("reqId", rid);
        try {
            chain.doFilter(req, res);
        } finally {
            MDC.clear();
        }
    }
}
