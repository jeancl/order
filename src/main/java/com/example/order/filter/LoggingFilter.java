package com.example.order.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code
    }

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
			long startTime = System.currentTimeMillis();
			chain.doFilter(request, response);
			long endTime = System.currentTimeMillis();
			log.info("Request URL: {} | Response Status: {} | Execution Time: {} ms", httpRequest.getRequestURI(), httpResponse.getStatus(), (endTime - startTime));
		} else {
			chain.doFilter(request, response);
		}
    }

    @Override
    public void destroy() {
        // Cleanup code
    }
}