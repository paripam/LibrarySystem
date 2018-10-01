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
import com.paripa.by.validator.FormValidator;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChangeBookInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        if (!FormValidator.isBookFormValid(req)) {
            return Const.REDIRECT_BOOK_EDIT_FORM;
        }
        Book book = setBook(req);
        String[] idAuthors = req.getParameterValues(Const.PARAM_AUTHORS);
        List<Author> authors = new ArrayList<>();
        for (String idAuthor : idAuthors) {
            Author author;
            try {
                author = new AuthorDao().findById(Integer.parseInt(idAuthor));
            } catch (DaoException e) {
                throw new ActionException();
            }
            authors.add(author);
        }
        book.setAuthors(authors);
        BookService bookService = new BookService();
        try {
            bookService.updateBook(book);
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.BOOK_FORM_MESSAGE, Const.UPDATE_SUCCESS);
        req.getSession().setAttribute(Const.BOOK, book);
        return Const.REDIRECT_BOOK_EDIT_FORM;
    }

    private Book setBook(HttpServletRequest req) {
        String title = req.getParameter(Const.PARAM_TITLE);
        String publisher = req.getParameter(Const.PARAM_PUBLISHER);
        String numberCopies = req.getParameter(Const.PARAM_COPIES);
        Book book = (Book) req.getSession().getAttribute(Const.BOOK);
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setNumberCopies(Integer.parseInt(numberCopies));
        return book;
    }
}
