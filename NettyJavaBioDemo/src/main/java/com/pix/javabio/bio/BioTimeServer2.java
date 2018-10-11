package com.pix.javabio.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 伪异步io实现时间服务器，采用线程池
 * 客户端发送"QUERY TIME ORDER"指令，服务器返回当前时间
 */
public class BioTimeServer2 {
    private final static  String TAG = "BioTimeServer2";
    private static final int PORT = 8899;
    private ServerSocket serverSocket;
    public void startServer() {
        System.out.println("CLASS "+ TAG +",startServer(),port:" + PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            Socket socket = null;
            // 创建线程池
            TimeServerHandlerExecutePool singleExecutor
                    = new TimeServerHandlerExecutePool(50,10000);
            while(true) {
                // 接收客户端请求
                socket = serverSocket.accept();
                // 线程池处理
                singleExecutor.execute(new TimeServerHandler(socket));

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(null != serverSocket) {
                System.out.println("CLASS " + TAG + ",server closed!");
                try {
                    serverSocket.close();
                    serverSocket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 服务器客户端连接处理
     */
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

    /**
     * 时间服务器执行线程池
     */
    private class TimeServerHandlerExecutePool {
        private ExecutorService executorService;
        public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize) {
            executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()
            ,maxPoolSize,120L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
        }
        public void execute(Runnable task) {
            executorService.execute(task);
        }
    }
}
