package com.paripa.by.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.paripa.by.constants.Const;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.Book;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BookDao extends EntityDao<Integer, Book> {

    private final static Logger logger = Logger.getLogger(BookDao.class);

    private final static String SELECT_ALL = "SELECT id_book, title, publisher, number_copies FROM library.book";
    private final static String SELECT_BY_ID = "SELECT id_book, title, publisher, number_copies FROM library.book WHERE id_book=?";
    private final static String UPDATE_BOOK_DETAILS = "UPDATE library.book SET title=?, publisher=?, number_copies=? WHERE id_book=?";
    private final static String INSERT = "INSERT INTO library.book(title, publisher, number_copies) VALUES(?, ?, ?)";

    private Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() throws DaoException {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Book book = retrieveBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of all books", e);
            throw new DaoException();
        }
        return books;
    }

    @Override
    public Book findById(Integer idBook) throws DaoException {
        Book book = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, idBook);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = retrieveBook(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get book by id", e);
            throw new DaoException();
        }
        return book;
    }

    @Override
    public void deleteById(Integer idBook) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer create(Book book) throws DaoException {
        int idBook = 0;
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getPublisher());
            statement.setInt(3, book.getNumberCopies());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                idBook = generatedKey.getInt(1);
            }
            generatedKey.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert book details", e);
            throw new DaoException();
        }
        return idBook;
    }

    @Override
    public void update(Book book) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_DETAILS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getPublisher());
            statement.setInt(3, book.getNumberCopies());
            statement.setInt(4, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update book details", e);
            throw new DaoException();
        }
    }

    private Book retrieveBook(ResultSet resultSet) throws DaoException {
        Book book = new Book();
        try {
            book.setId(resultSet.getInt(Const.BOOK_ID));
            book.setTitle(resultSet.getString(Const.BOOK_TITLE));
            book.setPublisher(resultSet.getString(Const.BOOK_PUBLISHER));
            book.setNumberCopies(resultSet.getInt(Const.BOOK_NUMBER_COPIES));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set for book", e);
            throw new DaoException();
        }
        return book;
    }
}


