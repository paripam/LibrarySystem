package com.paripa.by.action;

import com.paripa.by.action.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException;
}
