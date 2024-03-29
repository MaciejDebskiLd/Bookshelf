package com.example.bookshelf;

import com.example.bookshelf.controller.BookController;
import fi.iki.elonen.NanoHTTPD;

import java.sql.SQLException;

import static fi.iki.elonen.NanoHTTPD.Method.GET;
import static fi.iki.elonen.NanoHTTPD.Method.POST;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;

public class RequestUrlMapper {

    private final static String ADD_BOOK_URL = "/book/add";
    private final static String GET_BOOK_URL = "/book/get";
    private final static String GET_ALL_BOOK_URL = "/book/getAll";

    private BookController bookController = new BookController();

    public NanoHTTPD.Response delegateRequest (NanoHTTPD.IHTTPSession session) throws SQLException, ClassNotFoundException {

        if(GET.equals(session.getMethod()) && GET_BOOK_URL.equals(session.getUri())){
            return bookController.serveGetBookRequest(session);
        } else if (GET.equals(session.getMethod()) && GET_ALL_BOOK_URL.equals(session.getUri())){
            return bookController.serveGetBooksRequest(session);
        }else if (POST.equals(session.getMethod()) && ADD_BOOK_URL.equals(session.getUri())){
            return bookController.serveAddBookRequest(session);
        }
        return NanoHTTPD.newFixedLengthResponse(NOT_FOUND, "text/plain", "Not found");
    }
}
