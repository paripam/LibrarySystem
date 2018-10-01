package com.paripa.by.action.profile;


import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.UserDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String email = req.getParameter(Const.PARAM_EMAIL.toLowerCase());
        String password = req.getParameter(Const.PARAM_PASSWORD);
        User user;
        try {
            user = new UserDao().findByEmailAndPassword(email, String.valueOf(password.hashCode()));
        } catch (DaoException e) {
            throw new ActionException();
        }
        if (user == null ) {
            req.getSession().setAttribute(Const.LOGIN_MESSAGE, Const.LOGIN_ERROR);
            return Const.REDIRECT_START_PAGE;
        }
        req.getSession().setAttribute(Const.USER, user);
        req.getSession().setAttribute(Const.ROLE, user.getRole());
        req.getSession().setAttribute(Const.PASSWORD, password);
        return Const.REDIRECT_PROFILE;
    }
}