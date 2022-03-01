package com.lmm.aio.handler;

import com.lmm.aio.adapter.ChannelAdapter;
import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AioClientHandler extends ChannelAdapter {

  public AioClientHandler(AsynchronousSocketChannel channel, Charset charset) {
    super(channel, charset);
  }

  @Override
  public void channelActive(ChannelHandler ctx) {
    try {
      log.info("链接报告信息:{}", ctx.getChannel().getRemoteAddress());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void channelInactive(ChannelHandler ctx) {

  }

  @Override
  public void channelRead(ChannelHandler ctx, Object msg) {
    log.info("服务端收到：{},{}", new Date(), msg);
    ctx.writeAndFlush("客户端信息处理Successful！\r\n");
  }
}
