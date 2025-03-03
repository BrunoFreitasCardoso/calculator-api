package com.calculatorapi.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@WebFilter("/*")
public class UniqueIdentifierFilter implements Filter {

    public static final String REQUEST_ID_HEADER = "X-Request-ID";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uniqueId = httpRequest.getHeader(REQUEST_ID_HEADER);
        if (uniqueId == null || uniqueId.isBlank()) {
            uniqueId = UUID.randomUUID().toString();
        }
        httpRequest.setAttribute(REQUEST_ID_HEADER, uniqueId);
        MDC.put("requestId", uniqueId);

        // Attach the unique ID to the response header
        httpResponse.setHeader("X-Request-ID", uniqueId);

        // Proceed with the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
