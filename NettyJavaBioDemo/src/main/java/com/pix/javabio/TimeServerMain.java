package com.pix.javabio;

import com.pix.javabio.bio.BioTimeServer;

public class TimeServerMain {
    public static void main(String [] args) {
        System.out.println("Time server start!");
        BioTimeServer server = new BioTimeServer();
        server.startServer();
    }
}
