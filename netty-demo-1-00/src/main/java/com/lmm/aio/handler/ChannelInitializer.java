package com.lmm.aio.handler;

import com.lmm.aio.server.AioServer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public abstract class ChannelInitializer implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

  @Override
  public void completed(AsynchronousSocketChannel result, AioServer attachment) {
    try {
      initChannel(result);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      attachment.serverSocketChannel().accept(attachment, this);// 再此接收客户端连接
    }
  }

  @Override
  public void failed(Throwable exc, AioServer attachment) {

  }

  protected abstract void initChannel(AsynchronousSocketChannel channel) throws Exception;

}
