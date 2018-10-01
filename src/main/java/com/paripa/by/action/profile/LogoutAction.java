package com.paripa.by.action.profile;

import com.paripa.by.action.Action;
import com.paripa.by.constants.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return Const.REDIRECT_START_PAGE;
    }
}
