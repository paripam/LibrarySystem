package com.paripa.by.action.book;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.AuthorDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.Author;
import com.paripa.by.validator.FormValidator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import  com.paripa.by.validator.FormValidator;

public class AddAuthorAction implements Action {

    private final static Logger logger = Logger.getLogger(AddAuthorAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        if (!FormValidator.isAuthorFormValid(req)) {
            return Const.REDIRECT_AUTHOR_FORM;
        }
        String nameFirst = req.getParameter(Const.PARAM_NAME);
        String nameLast = req.getParameter(Const.PARAM_SURNAME);
        Author author = new Author();
        author.setNameFirst(nameFirst);
        author.setNameLast(nameLast);
        AuthorDao authorDao = new AuthorDao();
        try {
            authorDao.create(author);
        } catch (DaoException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.AUTHOR_FORM_MESSAGE, Const.AUTHOR_ADD_SUCCESS);
        logger.log(Level.INFO, "New author was added: " + author.toString());
        return Const.REDIRECT_AUTHOR_FORM;
    }
}

