package com.lmm.server;

import com.lmm.hanlder.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

  private final int port;

  public NettyServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws InterruptedException {
    if (args.length != 1) {
      log.error("Usage:{} port", NettyServer.class.getSimpleName());
      return;
    }
    Thread.sleep(1000);
    int port = Integer.parseInt(args[0]);
    new NettyServer(port).start();
    //Thread.sleep(100000);
  }

  public void start() throws InterruptedException {
    final ServerHandler serverHandler = new ServerHandler();
    //创建 EventLoopGroup
    EventLoopGroup group = new NioEventLoopGroup();
    //创建ServerBootstrap
    try {
      final ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap
          .group(group)//指定 NioEventLoopGroup 来接受和处理新的连接
          .channel(NioServerSocketChannel.class)//指定所使用的NIO传输Channel
          .localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字地址
          .childHandler(new ChannelInitializer<SocketChannel>() {
            //添加一个自定义的ServerHandler到子Channel的pipeline中
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast(serverHandler);
            }
          });
      //异地绑定服务器：调用sync（）方法阻塞等待直到绑定完成。阻塞当前线程
      ChannelFuture channelFuture = bootstrap.bind(port).sync();
      //获取Channel的closeFuture，并且阻塞当前线程直到它完成
      channelFuture.channel().closeFuture().sync();
      log.error("server start");
      //Thread.sleep(10000);
    } finally {
      //关闭 event 释放资源
      group.shutdownGracefully().sync();
    }
  }
}
