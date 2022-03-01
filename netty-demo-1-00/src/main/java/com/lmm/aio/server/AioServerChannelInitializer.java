package com.lmm.aio.server;

import com.lmm.aio.handler.AioServerHandler;
import com.lmm.aio.handler.ChannelInitializer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * 初始化
 */
public class AioServerChannelInitializer extends ChannelInitializer {

  @Override
  protected void initChannel(AsynchronousSocketChannel channel) throws Exception {
    channel
        .read(ByteBuffer.allocate(1024), 10, TimeUnit.SECONDS, null, new AioServerHandler(channel,
            Charset.forName("GBK")));
  }
}
