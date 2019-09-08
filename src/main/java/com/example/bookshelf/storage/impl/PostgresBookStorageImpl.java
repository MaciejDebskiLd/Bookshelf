package com.example.bookshelf.storage.impl;

import com.example.bookshelf.Book;
import com.example.bookshelf.storage.BookStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresBookStorageImpl implements BookStorage {

    private final static String JDBC_URL = "jdbc:postgresql://localhost:5433/book_store";
    private final static String DATABASE_USER = "postgres";
    private final static String DATABASE_PASS = "programator";

    @Override
    public Book getBook(long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, title, author, pagessum, yearofpublished, publishinghouse FROM books WHERE id = ?;)");

        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        Book book = new Book();

        if (resultSet.next()) {
            book.setId(id);
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setPagesSum(resultSet.getInt("pages_sum"));
            book.setYearOfPublished(resultSet.getInt("year_of_published"));
            book.setPublishingHouse(resultSet.getString("publishing_house"));

            return book;
        }
        preparedStatement.close();
        connection.close();
        return null;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        List<Book> bookStorage = new ArrayList<>();
        Connection connection = DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

        int i = 0;
        while ((resultSet.next())) {
            Book book = new Book();
            long id = resultSet.getInt(1);
            book.setId(id);
            String title = resultSet.getString("title");
            book.setTitle(title);
            String author = resultSet.getString(3);
            book.setAuthor(author);
            int pagessum = resultSet.getInt(4);
            book.setPagesSum(pagessum);
            int yearofpublished = resultSet.getInt(5);
            book.setYearOfPublished(yearofpublished);
            String publishinghouse = resultSet.getString(6);
            book.setPublishingHouse(publishinghouse);

            bookStorage.add(book);
            i++;

        }
        statement.close();
        connection.close();
        return bookStorage;
    }
    @Override
    public long addBook(Book book) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (id, title, author, pagessum, yearofpublished, publishinghouse) VALUES (nextval('sekwencja'),?, ?, ?, ?, ?) RETURNING id;");

        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setInt(3, book.getPagesSum());
        preparedStatement.setInt(4, book.getYearOfPublished());
        preparedStatement.setString(5, book.getPublishingHouse());


        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        long id = 0;
        while (resultSet.next()) {
            id = resultSet.getLong(1);

        }

//        preparedStatement.executeUpdate();


        preparedStatement.close();
        connection.close();

        return -1;
    }
}
