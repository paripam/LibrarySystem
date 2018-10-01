package com.paripa.by.action.book;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.AuthorDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.Author;
import com.paripa.by.entity.Book;
import com.paripa.by.service.BookService;
import com.paripa.by.service.exception.ServiceException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowCatalogueAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        List<Book> catalogue;
        List<Author> allAuthors;
        try {
            catalogue = new BookService().findAllBooks();
            allAuthors = new AuthorDao().findAll();
        } catch (ServiceException | DaoException e) {
            throw new ActionException();
        }
        req.setAttribute(Const.CATALOGUE, catalogue);
        req.setAttribute(Const.ALL_AUTHORS, allAuthors);
        return Const.FORWARD_CATALOGUE;
    }
}
