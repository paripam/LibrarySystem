package com.paripa.by.action.profile;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.UserDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.User;
import com.paripa.by.validator.FormValidator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePasswordAction implements Action {

    private final static Logger logger = Logger.getLogger(ChangePasswordAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String password = req.getParameter(Const.PARAM_PASSWORD);
        if (!FormValidator.isPasswordFormValid(req)) {
            return Const.REDIRECT_PROFILE_EDIT_FORM;
        }
        User user = (User) req.getSession().getAttribute(Const.USER);
        boolean isUpdated = !user.getPassword().equals(String.valueOf(password.hashCode()));
        if (!isUpdated) {
            return Const.REDIRECT_PROFILE_EDIT_FORM;
        }
        user.setPassword(String.valueOf(password.hashCode()));
        UserDao userDao = new UserDao();
        try {
            userDao.changePassword(user);
        } catch (DaoException e) {
            throw new ActionException();
        }
        logger.log(Level.INFO, "Password was changed for: " + user.toString());
        req.getSession().setAttribute(Const.PASSWORD_FORM_MESSAGE, Const.PASSWORD_UPDATE_SUCCESS);
        req.getSession().removeAttribute(Const.USER);
        return Const.REDIRECT_START_PAGE;
    }
}
