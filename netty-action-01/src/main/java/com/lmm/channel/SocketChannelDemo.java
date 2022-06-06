package com.lmm.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-06-05 17:09
 * @description: SocketChannel相关代码
 **/
@Slf4j
public class SocketChannelDemo {

  public static void main(String[] args) throws IOException {
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
    //设置非阻塞
    socketChannel.configureBlocking(false);

    ByteBuffer buffer = ByteBuffer.allocate(1024);

    socketChannel.read(buffer);
    socketChannel.close();
    log.error("end");

  }

}
