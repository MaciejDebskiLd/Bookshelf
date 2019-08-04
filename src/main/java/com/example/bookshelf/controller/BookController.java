package com.example.bookshelf.controller;

import com.example.bookshelf.Book;
import com.example.bookshelf.storage.BookStorage;
import com.example.bookshelf.storage.impl.StaticListBookStorageImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class BookController {

    private BookStorage bookStorage = new StaticListBookStorageImpl();

    public NanoHTTPD.Response serveGetBookRequest(NanoHTTPD.IHTTPSession session){
        return  null;
    }

    public NanoHTTPD.Response serveGetBooksRequest(NanoHTTPD.IHTTPSession session){
        ObjectMapper objectMapper = new ObjectMapper();
        String response = "";

        try {
            response = objectMapper.writeValueAsString(bookStorage.getAllBooks());
        }catch (JsonProcessingException e){
            System.err.println("Error during process request: \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error can't read all book");
        }return newFixedLengthResponse(OK, "application/json", response);
    }

    public NanoHTTPD.Response serveAddBookRequest(NanoHTTPD.IHTTPSession session){
       ObjectMapper objectMapper = new ObjectMapper();
       long randomBookId = System.currentTimeMillis();

       String lengthHeader = session.getHeaders().get("content-length");
       int contentLength = Integer.parseInt(lengthHeader);
       byte[] buffer = new byte[contentLength];

       try {
           session.getInputStream().read(buffer, 0, contentLength);
           String requestBody = new String(buffer).trim();
           Book requestBook = objectMapper.readValue(requestBody, Book.class);
           requestBook.setId(randomBookId);

           bookStorage.addBook(requestBook);

       }catch (Exception e){
           System.err.println("Error during process request: \n" + e);
           return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error book hasn't been added");
       }return newFixedLengthResponse(OK, "text/plain", "Book has been successfully added, id=" + randomBookId);
    }


}
