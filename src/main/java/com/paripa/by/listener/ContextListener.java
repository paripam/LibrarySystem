package com.paripa.by.listener;

import javax.servlet.*;

import com.paripa.by.constants.Const;
import com.paripa.by.db.ConnectionPool;
import com.paripa.by.entity.OrderStatus;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class ContextListener implements ServletContextListener {

    private final static Logger logger = Logger.getLogger(ContextListener.class);
    private ConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.log(Level.INFO, "The app was initialized");
        pool = ConnectionPool.getInstance();
        ServletContext context = event.getServletContext();
        OrderStatus[] allStatuses = OrderStatus.values();
        context.setAttribute(Const.ALL_STATUSES, allStatuses);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        pool.dispose();
    }
}
