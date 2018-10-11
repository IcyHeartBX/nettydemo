package com.pix.javabio;

import com.pix.javabio.bio.BioTimeClient;

public class TimeClientMain {
    public static void main(String [] args) {
        System.out.println("Time client start!");
        BioTimeClient client = new BioTimeClient();
        client.startClient();
    }
}
