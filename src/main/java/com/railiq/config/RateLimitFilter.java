package com.railiq.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import io.github.bucket4j.Refill;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket newBucket() {
    	Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        String path = httpReq.getRequestURI();

        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            String ip = httpReq.getRemoteAddr();
            Bucket bucket = buckets.computeIfAbsent(ip, k -> newBucket());
            if (!bucket.tryConsume(1)) {
                ((HttpServletResponse) res).setStatus(429);
                res.getWriter().write("Too many attempts, try again in a minute.");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}