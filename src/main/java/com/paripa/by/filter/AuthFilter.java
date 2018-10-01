package com.paripa.by.filter;

import com.paripa.by.constants.Const;
import com.paripa.by.entity.UserRole;

import java.util.List;
import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter implements Filter {

    private List<String> allowedURLs;

    @Override
    public void init(FilterConfig filterConfig) {
        allowedURLs = new ArrayList<>();
        allowedURLs.add("/login");
        allowedURLs.add("/new-reader");
        allowedURLs.add("/change-lang");
        allowedURLs.add("/register");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute(Const.USER) == null && !allowedURLs.contains(req.getPathInfo())) {
            req.getSession().setAttribute(Const.ROLE, UserRole.GUEST);
            request.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
