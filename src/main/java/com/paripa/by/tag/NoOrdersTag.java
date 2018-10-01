package com.paripa.by.tag;

import com.paripa.by.entity.Order;

import java.util.List;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class NoOrdersTag extends SimpleTagSupport {

    private List<Order> orders;

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void doTag() throws IOException, JspException {
        if (orders.size() == 0) {
            getJspBody().invoke(null);
        }
    }
}
