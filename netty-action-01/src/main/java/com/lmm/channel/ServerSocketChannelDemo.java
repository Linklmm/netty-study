package com.lmm.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-06-05 16:49
 * @description: ServerSocketChannel相关代码
 **/
@Slf4j
public class ServerSocketChannelDemo {

  public static void main(String[] args) throws IOException, InterruptedException {
    // 端口号
    int port = 8888;
    //创建 buffer
    ByteBuffer buffer = ByteBuffer.wrap("hello NIO".getBytes());
    //创建ServerSocketChannel
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    //绑定
    serverSocketChannel.socket().bind(new InetSocketAddress(port));

    //设置非阻塞模式
    serverSocketChannel.configureBlocking(false);
    while (true) {
      log.error("waiting for connections");
      SocketChannel sc = serverSocketChannel.accept();
      //没有连接接入
      if (null == sc) {
        log.error("null");
        Thread.sleep(2000);
      } else {
        log.error("incoming connection from:{}", sc.socket().getRemoteSocketAddress());
        buffer.rewind();
        sc.write(buffer);
        sc.close();
      }
    }
  }

}
