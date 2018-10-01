package com.paripa.by.controller;

import com.paripa.by.action.Action;
import com.paripa.by.action.ActionFactory;
import com.paripa.by.action.exception.ActionException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LibrarySystemController extends HttpServlet {

    private final static String METHOD_GET = "GET";
    private final static String METHOD_POST = "POST";

    @Override
    public void init() {}

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        handleRequest(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = ActionFactory.defineAction(req);
        String resultPage;
        try {
            resultPage = action.execute(req, resp);
        } catch (ActionException e) {
            throw new ServletException();
        }
        if (req.getMethod().equals(METHOD_POST)) {
            resp.sendRedirect(resultPage);
        } else if (req.getMethod().equals(METHOD_GET)){
            req.getRequestDispatcher("/jsp/" + resultPage + ".jsp").forward(req,resp);
        }
    }

    @Override
    public void destroy() {}
}
