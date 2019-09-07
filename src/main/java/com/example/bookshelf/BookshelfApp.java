package com.example.bookshelf;


import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.sql.SQLException;

public class BookshelfApp extends NanoHTTPD {

    RequestUrlMapper requestUrlMapper = new RequestUrlMapper();

    public BookshelfApp(int port) throws IOException{
        super(port);
        start(5000, false);
        System.out.println("Server has been started");
    }

    public static void main(String[] args) {
        try{
            new BookshelfApp(8081);

        }catch (IOException e){
            System.err.println("Server can't start because of error: \n " + e);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            return requestUrlMapper.delegateRequest(session);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } return null;
    }
}
