package com.pix.javabio.bio;

import java.lang.reflect.Executable;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTimeServer {
    private static final int PORT = 8899;
    private ServerSocket serverSocket;
    public void startServer() {
        System.out.println("CLASS BioTimeServer,startServer(),port:" + PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            Socket socket = null;
            while(true) {
                // 接收客户端请求
                socket = serverSocket.accept();
                // 创建处理线程
                new Thread(new TimeServerHandler(socket)).start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

        }
    }

    private class TimeServerHandler implements Runnable{
        private Socket socket;
        public TimeServerHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {

        }
    }
}
