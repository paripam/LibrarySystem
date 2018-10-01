package com.paripa.by.action.profile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.UserDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.User;
import com.paripa.by.validator.FormValidator;


public class ChangeProfileInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String nameFirstNew = req.getParameter(Const.PARAM_NAME);
        String nameLastNew = req.getParameter(Const.PARAM_SURNAME);
        User user = (User) req.getSession().getAttribute(Const.USER);
        if (!FormValidator.isEditProfileFormValid(req)) {
            return Const.REDIRECT_PROFILE_EDIT_FORM;
        }
        boolean isUpdated = !user.getNameFirst().equals(nameFirstNew)
                || !user.getNameLast().equals(nameLastNew);
        if (!isUpdated) {
            return Const.REDIRECT_PROFILE_EDIT_FORM;
        }
        user.setNameFirst(nameFirstNew);
        user.setNameLast(nameLastNew);
        UserDao userDao = new UserDao();
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.PROFILE_FORM_MESSAGE, Const.UPDATE_SUCCESS);
        return Const.REDIRECT_PROFILE_EDIT_FORM;
    }
}
