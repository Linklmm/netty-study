package com.lmm.hanlder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

//表示一个Channel-Handler可以被多个Channel安全地共享
@Sharable
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf in = (ByteBuf) msg;

    log.error("收到客户端的消息");
    //打印接收到的消息
    log.error("Server received:{}", in.toString(CharsetUtil.UTF_8));
    //将接收到的消息写给发送者，而不冲刷出站消息
    ctx.write(Unpooled.copiedBuffer("hello ，我是服务端", CharsetUtil.UTF_8));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    //将暂存与ChannelOutBoundBuffer中的消息冲刷到节点中，并且关闭该channel
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    //打印异常
    log.error("happen exception", cause);
    ctx.close();
  }
}
