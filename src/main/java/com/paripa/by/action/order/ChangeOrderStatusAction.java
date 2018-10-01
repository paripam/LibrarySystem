package com.paripa.by.action.order;


import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.OrderDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.OrderStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeOrderStatusAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idParameter = req.getParameter(Const.PARAM_ORDER_ID);
        String statusParameter = req.getParameter(Const.PARAM_STATUS);
        if (idParameter == null || statusParameter == null) {
            throw new ActionException();
        }
        int idOrder = Integer.parseInt(idParameter);
        OrderStatus status = OrderStatus.valueOf(statusParameter);
        OrderDao orderDao = new OrderDao();
        try {
            orderDao.changeStatus(idOrder, status);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Const.REDIRECT_PROFILE;
    }
}
