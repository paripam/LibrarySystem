package com.paripa.by.action.book;

import java.util.List;
import java.util.ArrayList;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.entity.Author;
import com.paripa.by.entity.Book;
import com.paripa.by.service.BookService;
import com.paripa.by.service.exception.ServiceException;
import com.paripa.by.validator.FormValidator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddBookAction implements Action {

    private final static Logger logger = Logger.getLogger(AddBookAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        if (!FormValidator.isBookFormValid(req)) {
            return Const.REDIRECT_CATALOGUE;
        }
        String title = req.getParameter(Const.PARAM_TITLE);
        String publisher = req.getParameter(Const.PARAM_PUBLISHER);
        String numberCopiesString = req.getParameter(Const.PARAM_COPIES);
        String[] idAuthors = req.getParameterValues(Const.PARAM_AUTHORS);
        List<Author> authors = new ArrayList<>();
        for (String idAuthor : idAuthors) {
            Author author = new Author();
            author.setId(Integer.parseInt(idAuthor));
            authors.add(author);
        }
        Book book = new Book();
        book.setAuthors(authors);
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setNumberCopies(Integer.parseInt(numberCopiesString));
        BookService bookService = new BookService();
        try {
            bookService.createBook(book);
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.BOOK_FORM_MESSAGE, Const.BOOK_ADD_SUCCESS);
        logger.log(Level.INFO, "New book was added: ID#" + book.getId() + " " + book.getTitle());
        return Const.REDIRECT_CATALOGUE;
    }
}
