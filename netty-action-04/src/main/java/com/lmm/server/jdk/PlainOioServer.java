package com.lmm.server.jdk;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 * 未使用Netty的阻塞网络编程
 */
@Slf4j
public class PlainOioServer {

  public void server(int port) throws IOException {
    //绑定端口
    final ServerSocket serverSocket = new ServerSocket(port);
    for (; ; ) {
      //接受连接
      final Socket clientSocket = serverSocket.accept();
      log.error("Accepted connection from " + clientSocket);
      //创建一个线程来处理该连接
      new Thread(new Runnable() {
        public void run() {
          OutputStream out;
          try {
            out = clientSocket.getOutputStream();
            //将消息写给已连接的客户端（发送消息给客户端）
            out.write("Hi！我是服务端\r\n".getBytes(Charset.forName("utf-8")));
            out.flush();
            //关闭连接
            clientSocket.close();
          } catch (IOException e) {
            e.printStackTrace();
          } finally {
            try {
              clientSocket.close();
            } catch (IOException e) {
              log.error("close happen exception, ", e);
            }
          }
        }
      }).start();
    }
  }

  public static void main(String[] args) throws IOException {
    PlainOioServer server = new PlainOioServer();
    server.server(9999);
  }

}
