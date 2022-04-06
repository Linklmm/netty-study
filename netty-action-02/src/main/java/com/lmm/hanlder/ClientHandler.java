package com.lmm.hanlder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Sharable
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

  //将在一个连接建立时被调用
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    //当被通知的channel是活跃的时候，发送一条消息
    log.error("======链接建立=====");
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello ，我是客户端", CharsetUtil.UTF_8));
  }

  //每当接收数据时，都会调用这个方法
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf in)
      throws Exception {
    //接收服务端发送来的消息
    log.error("收到服务器的消息");
    log.error("client received:{}", in.toString(CharsetUtil.UTF_8));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    log.error("client happen exception", cause);
    ctx.close();
  }
}
