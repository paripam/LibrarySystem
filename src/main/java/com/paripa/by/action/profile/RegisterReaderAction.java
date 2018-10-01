package com.paripa.by.action.profile;

import java.sql.Date;
import java.util.Calendar;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.UserDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.User;
import com.paripa.by.entity.UserRole;
import com.paripa.by.validator.FormValidator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterReaderAction implements Action {

    private final static Logger logger = Logger.getLogger(RegisterReaderAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        if (!FormValidator.isNewUserFormValid(req)) {
            return Const.REDIRECT_READER_FORM;
        }
        String email = req.getParameter(Const.PARAM_EMAIL);
        boolean isRegistered;
        try {
            isRegistered = new UserDao().isRegistered(email);
        } catch (DaoException e) {
            throw new ActionException();
        }
        if (isRegistered) {
            req.getSession().setAttribute(Const.REGISTRATION_MESSAGE, Const.EMAIL_EXIST_ERROR);
            return Const.REDIRECT_READER_FORM;
        }
        User user = setUser(req);
        UserDao userDao = new UserDao();
        try {
            userDao.create(user);
        } catch (DaoException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.REGISTRATION_MESSAGE, Const.REGISTRATION_SUCCESS);
        logger.log(Level.INFO, "New reader was registered: " + user.toString());
        return Const.REDIRECT_START_PAGE;
    }

    private User setUser(HttpServletRequest req) {
        User user = new User();
        user.setEmail(req.getParameter(Const.PARAM_EMAIL));
        user.setPassword(String.valueOf(req.getParameter(Const.PARAM_PASSWORD).hashCode()));
        user.setRole(UserRole.READER);
        user.setNameFirst(req.getParameter(Const.PARAM_NAME));
        user.setNameLast(req.getParameter(Const.PARAM_SURNAME));
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        user.setDateRegistered(currentDate);
        return user;
    }
}
