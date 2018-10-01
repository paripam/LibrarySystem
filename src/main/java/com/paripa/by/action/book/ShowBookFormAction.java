package com.paripa.by.action.book;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.AuthorDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.Author;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowBookFormAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String resultPage = null;
        if (req.getSession().getAttribute(Const.BOOK) != null) {
            List<Author> allAuthors;
            try {
                allAuthors = new AuthorDao().findAll();
            } catch (DaoException e) {
                throw new ActionException();
            }
            req.getSession().setAttribute(Const.ALL_AUTHORS, allAuthors);
            resultPage = Const.FORWARD_EDIT_BOOK_FORM;
        }
        return resultPage;
}
}
