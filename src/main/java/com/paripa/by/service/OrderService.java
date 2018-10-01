package com.paripa.by.service;

import com.paripa.by.dao.AuthorDao;
import com.paripa.by.dao.BookDao;
import com.paripa.by.dao.OrderDao;
import com.paripa.by.dao.UserDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.db.ConnectionPool;
import com.paripa.by.entity.Author;
import com.paripa.by.entity.Book;
import com.paripa.by.entity.Order;
import com.paripa.by.entity.User;
import com.paripa.by.service.exception.ServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private final static Logger logger = Logger.getLogger(OrderService.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    public OrderService() { }

    public List<Order> findAllOrders() throws ServiceException {
        List<Order> orders;
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            OrderDao orderDao = new OrderDao(connection);
            UserDao userDao = new UserDao(connection);
            BookDao bookDao = new BookDao(connection);
            AuthorDao authorDao = new AuthorDao(connection);
            orders = orderDao.findAll();
            for (Order order : orders) {
                User user = userDao.findById(order.getUser().getId());
                order.setUser(user);
                Book book = bookDao.findById(order.getBook().getId());
                if (book != null) {
                    List<Author> authors = authorDao.findAuthorsOfBook(book);
                    book.setAuthors(authors);
                    order.setBook(book);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of getting list of all orders", e1);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e2) {
                logger.log(Level.ERROR, "Unable to rollback transaction", e2);
            }
            throw new ServiceException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Unable to close connection", e);
            }
        }
        return orders;
    }

    public List<Order> findOrdersOfUser(User user) throws ServiceException {
        List<Order> orders;
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            OrderDao orderDao = new OrderDao(connection);
            BookDao bookDao = new BookDao(connection);
            AuthorDao authorDao = new AuthorDao(connection);
            orders = orderDao.findOrdersOfUser(user);
            for (Order order : orders) {
                order.setUser(user);
                Book book = bookDao.findById(order.getBook().getId());
                if (book != null) {
                    List<Author> authors = authorDao.findAuthorsOfBook(book);
                    book.setAuthors(authors);
                    order.setBook(book);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of finding orders of user", e1);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e2) {
                logger.log(Level.ERROR, "Unable to rollback transaction", e2);
            }
            throw new ServiceException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Unable to close connection", e);
            }
        }
        return orders;
    }
}

