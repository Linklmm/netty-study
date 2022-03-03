package com.lmm.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty服务端示例
 */
public class NettyServer {

  private void bind(int port) {
    //配置服务端NIO 线程组
    EventLoopGroup parentGroup = new NioEventLoopGroup();
    EventLoopGroup childGroup = new NioEventLoopGroup();

    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(parentGroup, childGroup)
        .channel(NioServerSocketChannel.class)//非阻塞模式
        .option(ChannelOption.SO_BACKLOG, 128)
        .childHandler(new MyChannelInitializer());
    try {
      ChannelFuture future = serverBootstrap.bind(port).sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      childGroup.shutdownGracefully();
      parentGroup.shutdownGracefully();
    }

  }

  public static void main(String[] args) throws InterruptedException {
    new NettyServer().bind(8080);
    Thread.sleep(100000);
  }
}
