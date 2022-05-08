package com.lmm;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-02 17:36
 * @description: 从Channel引导客户端，当代理服务器一样，既当服务端，又当客户端，对收到的消息进行转发
 **/
@Slf4j
public class ChannelClient {

  public static void main(String[] args) {
    final ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
        .channel(NioServerSocketChannel.class)
        .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
          ChannelFuture channelFuture;

          @Override
          public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //创建一个BootStrap连接远程节点
            Bootstrap clientBootStrap = new Bootstrap();
            clientBootStrap.channel(NioSocketChannel.class).handler(
                new SimpleChannelInboundHandler<ByteBuf>() {
                  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
                      throws Exception {
                    log.error("received data");
                  }
                });
            clientBootStrap.group(ctx.channel().eventLoop());
            channelFuture = clientBootStrap.connect(new InetSocketAddress("www.manning.com", 80));
          }

          protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
          }
        });
    ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
    future.addListener(new ChannelFutureListener() {
      public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
          log.error("server bound");
        } else {
          log.error("bind attempt failed");
        }
      }
    });
  }

}
