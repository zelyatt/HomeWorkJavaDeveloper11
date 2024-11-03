package org.example;

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
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String timezone = req.getParameter("timezone");

        if (timezone != null && !timezone.isEmpty()) {
            if (TimeZone.getTimeZone(timezone).getID().equals("GMT") && !timezone.equals("GMT")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
