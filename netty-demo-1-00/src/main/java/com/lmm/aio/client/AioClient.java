package com.lmm.aio.client;

import com.lmm.aio.handler.AioClientHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

/**
 * aio客户端
 */
@Slf4j
public class AioClient {

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
    AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
    Future<Void> future = socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
    log.info("aio netty client start done");
    future.get();
    socketChannel.read(ByteBuffer.allocate(1024), null,
        new AioClientHandler(socketChannel, Charset.forName("GBK")));
    Thread.sleep(100000);
  }
}
