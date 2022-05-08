package com.lmm;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-02 17:13
 * @description: 引导一个客户端
 **/
@Slf4j
public class TestBootStrap {

  public static void main(String[] args) {
    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(group)
        .channel(NioSocketChannel.class)
        .handler(new SimpleChannelInboundHandler<ByteBuf>() {
          protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            log.error("Received data");
          }
        });

    ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
    future.addListener(new ChannelFutureListener() {
      public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
          log.error("connection is success");
        } else {
          log.error("connection attempt failed");
          future.cause().printStackTrace();
        }
      }
    });

  }

}
