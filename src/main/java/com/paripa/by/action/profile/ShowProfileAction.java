package com.paripa.by.action.profile;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.entity.Order;
import com.paripa.by.entity.User;
import com.paripa.by.service.OrderService;
import com.paripa.by.service.exception.ServiceException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowProfileAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String profilePage = null;
        User user = (User) req.getSession().getAttribute(Const.USER);
        OrderService orderService = new OrderService();
        List<Order> orders = null;
        try {
            switch (user.getRole()) {
                case LIBRARIAN:
                    orders = orderService.findAllOrders();
                    profilePage = Const.FORWARD_LIBRARIAN_PROFILE;
                    break;
                case READER:
                    orders = orderService.findOrdersOfUser(user);
                    profilePage = Const.FORWARD_READER_PROFILE;
                    break;
                case GUEST:
                    profilePage = Const.FORWARD_START;
                    break;
            }
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.ORDERS, orders);
        return profilePage;
    }
}
