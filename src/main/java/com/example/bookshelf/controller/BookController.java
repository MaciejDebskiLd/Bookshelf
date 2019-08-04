package com.example.bookshelf.controller;

import com.example.bookshelf.storage.BookStorage;
import com.example.bookshelf.storage.impl.StaticListBookStorageImpl;
import fi.iki.elonen.NanoHTTPD;

public class BookController {

    private BookStorage bookStorage = new StaticListBookStorageImpl();

    public NanoHTTPD.Response serveGetBookRequest(NanoHTTPD.IHTTPSession session){
        return  null;
    }

    public NanoHTTPD.Response serveGetBooksRequest(NanoHTTPD.IHTTPSession session){
        return  null;
    }

    public NanoHTTPD.Response serveAddBookRequest(NanoHTTPD.IHTTPSession session){
        return  null;
    }


}
