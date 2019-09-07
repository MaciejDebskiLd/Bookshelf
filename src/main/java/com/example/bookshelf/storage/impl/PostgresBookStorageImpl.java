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
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, title, author, pagessum, yearofpublished, publishinghouse FROM books WHERE id = ?);");

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

        while ((resultSet.next())) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString("title");
            String author = resultSet.getString(3);
            int pagessum = resultSet.getInt(4);
            int yearofpublished = resultSet.getInt(5);
            String publishinghouse = resultSet.getString(6);
            Book book = new Book(id, title, author, pagessum, yearofpublished, publishinghouse);
            bookStorage.add(book);
            return bookStorage;
        }


        statement.close();
        connection.close();
        return bookStorage;
    }

    @Override
    public void addBook(Book book) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (id, title, author, pagessum, yearofpublished, publishinghouse) VALUES (?, ?, ?, ?, ?, ?);");

        preparedStatement.setInt(1, 5);
        preparedStatement.setString(2, "Gomorra");
        preparedStatement.setString(3, "Jan Kosa");
        preparedStatement.setInt(4, 200);
        preparedStatement.setInt(5, 1999);
        preparedStatement.setString(6, "Oko");

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
