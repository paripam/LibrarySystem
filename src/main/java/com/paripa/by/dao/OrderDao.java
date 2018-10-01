package com.paripa.by.dao;


import com.paripa.by.constants.Const;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.db.ConnectionPool;
import com.paripa.by.entity.Book;
import com.paripa.by.entity.Order;
import com.paripa.by.entity.OrderStatus;
import com.paripa.by.entity.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends EntityDao<Integer, Order> {

    private final static Logger logger = Logger.getLogger(OrderDao.class);

    private final static String SELECT_ALL = "SELECT id_order, id_book, id_user, status, date FROM library.order ORDER BY date DESC";
    private final static String SELECT_BY_USER_ID = "SELECT id_order, id_book, id_user, status, date FROM library.order WHERE id_user=? ORDER BY date DESC";
    private final static String UPDATE_STATUS_BY_ORDER_ID = "UPDATE library.order SET status=? WHERE id_order=?";
    private final static String DELETE_BY_ORDER_ID = "DELETE FROM library.order WHERE id_order=?";
    private final static String INSERT = "INSERT INTO library.order (id_book, id_user, status) VALUES(?, ?, ?)";

    private Connection connection;
    private ConnectionPool pool = ConnectionPool.getInstance();

    public OrderDao() { }

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = retrieveOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of all orders", e);
            throw new DaoException();
        }
        return orders;
    }

    @Override
    public Order findById(Integer id){
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer idOrder) throws DaoException {
        try (Connection connection = pool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ORDER_ID)) {
            statement.setInt(1, idOrder);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete order" ,e);
            throw new DaoException();
        }
    }

    @Override
    public Integer create(Order order) throws DaoException {
        int idOrder = 0;
        try (Connection connection = pool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getBook().getId());
            statement.setInt(2, order.getUser().getId());
            statement.setString(3, String.valueOf(order.getStatus()));
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                idOrder = generatedKey.getInt(1);
            }
            generatedKey.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert order", e);
            throw new DaoException();
        }
        return idOrder;
    }

    @Override
    public void update(Order entity) throws DaoException {
        throw new DaoException(new UnsupportedOperationException());
    }

    public List<Order> findOrdersOfUser(User user) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = retrieveOrder(resultSet);
                order.setUser(user);
                orders.add(order);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get user's list of orders", e);
            throw new DaoException();
        }
        return orders;
    }

    public void changeStatus(int idOrder, OrderStatus status) throws DaoException {
        try (Connection connection = pool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_BY_ORDER_ID)) {
            statement.setString(1, String.valueOf(status));
            statement.setInt(2, idOrder);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update status", e);
            throw new DaoException();
        }
    }

    private Order retrieveOrder(ResultSet resultSet) throws DaoException {
        Order order = new Order();
        User user = new User();
        Book book = new Book();
        try {
            order.setId(resultSet.getInt(Const.ORDER_ID));
            order.setDate(resultSet.getDate(Const.ORDER_DATE));
            order.setStatus(OrderStatus.valueOf(resultSet.getString(Const.ORDER_STATUS)));
            book.setId(resultSet.getInt(Const.BOOK_ID));
            user.setId(resultSet.getInt(Const.USER_ID));
            order.setBook(book);
            order.setUser(user);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set for order", e);
            throw new DaoException();
        }
        return order;
    }
}