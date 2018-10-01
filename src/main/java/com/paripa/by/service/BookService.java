package com.paripa.by.service;

import com.paripa.by.dao.AuthorDao;
import com.paripa.by.dao.BookDao;
import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.db.ConnectionPool;
import com.paripa.by.entity.Author;
import com.paripa.by.entity.Book;
import com.paripa.by.service.exception.ServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final static Logger logger = Logger.getLogger(BookService.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    public BookService() { }

    public List<Book> findAllBooks() throws ServiceException {
        List<Book> books;
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            BookDao bookDao = new BookDao(connection);
            AuthorDao authorDao = new AuthorDao(connection);
            books = bookDao.findAll();
            for (Book book : books) {
                List<Author> authors = authorDao.findAuthorsOfBook(book);
                book.setAuthors(authors);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of getting list of books", e1);
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
        return books;
    }

    public Book findBookById(int idBook) throws ServiceException {
        Book book;
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            BookDao bookDao = new BookDao(connection);
            AuthorDao authorDao = new AuthorDao(connection);
            book = bookDao.findById(idBook);
            if (book != null) {
                List<Author> authors = authorDao.findAuthorsOfBook(book);
                book.setAuthors(authors);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of getting book by id", e1);
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
        return book;
    }

    public void createBook(Book book) throws ServiceException {
        Connection connection = null;
        int idBook;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            BookDao bookDao = new BookDao(connection);
            AuthorDao authorDao = new AuthorDao(connection);
            idBook = bookDao.create(book);
            book.setId(idBook);
            authorDao.insertAuthorsOfBook(book);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of saving a book", e1);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e2) {
                logger.log(Level.ERROR, "Unable to rollback transaction", e2);
            }
            throw new ServiceException();
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Unable to close connection", e);
            }
        }
    }

    public void updateBook(Book book) throws ServiceException {
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            BookDao bookDao = new BookDao(connection);
            AuthorDao authorDao = new AuthorDao(connection);
            bookDao.update(book);
            authorDao.deleteAuthorsOfBook(book);
            authorDao.insertAuthorsOfBook(book);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException |DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of updating book details", e1);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e2) {
                logger.log(Level.ERROR, "Unable to rollback transaction", e2);
            }
            throw new ServiceException();
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Unable to close connection", e);
            }
        }
    }
}

