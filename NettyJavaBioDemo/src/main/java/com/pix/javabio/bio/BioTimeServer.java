package com.pix.javabio.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 阻塞式时间服务器类
 * 客户端发送"QUERY TIME ORDER"指令，服务器返回当前时间
 */
public class BioTimeServer {
    private final static  String TAG = "BioTimeServer";
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
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                // 获取输入流
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                // 获取输出流
                out = new PrintWriter(this.socket.getOutputStream(),true);
                String currentTime = null;
                String order = null;
                while(true) {
                    order = in.readLine();
                    if(null == order) {
                        return ;
                    }
                    System.out.println("CLASS " + TAG + ",receiv client order:" + order);
                    currentTime = "QUERY TIME ORDER".equalsIgnoreCase(order)
                            ?new Date(System.currentTimeMillis()).toString()
                            : "BAD ORDER";
                    // 写出时间
                    out.println(currentTime);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if(null != in) {
                        in.close();
                        in = null;
                    }
                    if(null != out) {
                        out.close();
                        out = null;
                    }
                    if(null != this.socket) {
                        this.socket.close();
                        this.socket = null;
                    }

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
