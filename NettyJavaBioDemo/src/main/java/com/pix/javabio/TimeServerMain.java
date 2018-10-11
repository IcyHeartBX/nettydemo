package com.pix.javabio;

import com.pix.javabio.bio.BioTimeServer;
import com.pix.javabio.bio.BioTimeServer2;

public class TimeServerMain {
    public static void main(String [] args) {
        System.out.println("Time server start!");
//        BioTimeServer server = new BioTimeServer();
        BioTimeServer2 server = new BioTimeServer2();
        server.startServer();
    }
}
