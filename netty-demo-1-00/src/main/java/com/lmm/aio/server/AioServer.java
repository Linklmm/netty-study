package com.lmm.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AioServer extends Thread {

  private AsynchronousServerSocketChannel serverSocketChannel;

  @Override
  public void run() {
    try {
      serverSocketChannel = AsynchronousServerSocketChannel
          .open(AsynchronousChannelGroup.withCachedThreadPool(
              Executors.newCachedThreadPool(), 10));
      serverSocketChannel.bind(new InetSocketAddress(8080));
      log.info("netty Server start done");

      CountDownLatch latch = new CountDownLatch(1);
      serverSocketChannel.accept(this, new AioServerChannelInitializer());
      latch.await();
    } catch (IOException e) {
      log.error("happen IOException", e);
    } catch (InterruptedException e) {
      log.error("happen InterruptedException", e);
    }
  }

  public AsynchronousServerSocketChannel serverSocketChannel() {
    return serverSocketChannel;
  }

  public static void main(String[] args) throws InterruptedException {
    new AioServer().start();
    Thread.sleep(100000);
  }
}
