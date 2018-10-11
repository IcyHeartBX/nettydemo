package com.pix.javabio.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 阻塞式同步获取时间Tcp客户端
 * 通过发送"QUERY TIME ORDER"来获取服务器时间
 */
public class BioTimeClient {
    private static final String TAG = "BioTimeClient";
    private static final int PORT = 8899;
    private static final String ORDER = "QUERY TIME ORDER";

    public void startClient() {
        System.out.println("CLASS " + TAG + ",startClient(),prot:" + PORT);
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 创建套接字
            socket = new Socket("127.0.0.1",PORT);
            // 输入流
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 输出流
            out = new PrintWriter(socket.getOutputStream(),true);
            // 发送查询指令
            out.println(ORDER);
            System.out.println("CLASS" + TAG + ",startClient(), send query order success!");
            // 读取服务器返回值
            String respond = in.readLine();
            System.out.println("CLASS" + TAG + ",startClient(), receive server msg:" + respond);
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
                if(null != socket) {
                    socket.close();
                    socket = null;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
