package com.paripa.by.action.order;


import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.OrderDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.Book;
import com.paripa.by.entity.Order;
import com.paripa.by.entity.OrderStatus;
import com.paripa.by.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderBookAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idBookString = req.getParameter(Const.PARAM_BOOK_ID);
        if (idBookString == null) {
            throw new ActionException();
        }
        int idOrderedBook = Integer.parseInt(idBookString);
        Book orderedBook = new Book();
        orderedBook.setId(idOrderedBook);
        User user = (User) req.getSession().getAttribute(Const.USER);
        Order order = new Order();
        order.setBook(orderedBook);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        OrderDao orderDao = new OrderDao();
        try {
            orderDao.create(order);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Const.REDIRECT_PROFILE;
    }
}
