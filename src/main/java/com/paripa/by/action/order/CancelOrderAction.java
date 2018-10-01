package com.paripa.by.action.order;


import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.OrderDao;
import com.paripa.by.dao.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CancelOrderAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idOrderString = req.getParameter(Const.PARAM_ORDER_ID);
        if (idOrderString == null) {
            throw new ActionException();
        }
        int idOrder = Integer.parseInt(idOrderString);
        OrderDao orderDao = new OrderDao();
        try {
            orderDao.deleteById(idOrder);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Const.REDIRECT_PROFILE;
    }
}
