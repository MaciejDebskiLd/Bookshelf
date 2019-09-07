package com.example.bookshelf.storage;

import com.example.bookshelf.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookStorage {

    Book getBook(long id) throws SQLException, ClassNotFoundException;
    List<Book> getAllBooks() throws SQLException, ClassNotFoundException;
    void addBook(Book book) throws SQLException;
}
