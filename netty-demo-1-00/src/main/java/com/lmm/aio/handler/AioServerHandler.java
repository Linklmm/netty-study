package com.lmm.aio.handler;


import com.lmm.aio.adapter.ChannelAdapter;
import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端消息处理
 */
@Slf4j
public class AioServerHandler extends ChannelAdapter {

  public AioServerHandler(AsynchronousSocketChannel channel, Charset charset) {
    super(channel, charset);
  }

  @Override
  public void channelActive(ChannelHandler ctx) {
    try {
      log.info("链接报告信息:{}", ctx.getChannel().getRemoteAddress());
      ctx.writeAndFlush(
          "通知服务端链接建立成功：" + new Date() + " " + ctx.getChannel().getRemoteAddress() + "\r\n");
    } catch (IOException e) {
      log.error("channel active happen exception", e);
    }
  }

  @Override
  public void channelInactive(ChannelHandler ctx) {

  }

  @Override
  public void channelRead(ChannelHandler ctx, Object msg) {
    log.info("服务端收到:{},msg:{}", new Date(), msg);
    ctx.writeAndFlush("服务端信息处理Successful！\r\n");
  }
}
