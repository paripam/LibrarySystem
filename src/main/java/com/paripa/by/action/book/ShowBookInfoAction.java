package com.paripa.by.action.book;


import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.entity.Book;
import com.paripa.by.service.BookService;
import com.paripa.by.service.exception.ServiceException;
import com.paripa.by.validator.FieldValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBookInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String resultPage = null;
        String idParameter = req.getParameter(Const.PARAM_BOOK_ID);
        boolean idValid = FieldValidator.isIntegerValid(idParameter);
        if (idValid) {
            int idBook = Integer.parseInt(idParameter);
            Book book;
            try {
                book = new BookService().findBookById(idBook);
            } catch (ServiceException e) {
                throw new ActionException();
            }
            req.getSession().setAttribute(Const.BOOK, book);
            resultPage = Const.FORWARD_BOOK_INFO;
        } else if (req.getSession().getAttribute(Const.BOOK) != null) {
            resultPage = Const.FORWARD_BOOK_INFO;
        }
        return resultPage;
    }
}