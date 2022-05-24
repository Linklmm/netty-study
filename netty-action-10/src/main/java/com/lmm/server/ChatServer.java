package com.lmm.server;

import com.lmm.init.ChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-22 15:35
 * @description: 引导服务器
 **/
@Slf4j
public class ChatServer {

  private final ChannelGroup channelGroup = new DefaultChannelGroup(
      ImmediateEventExecutor.INSTANCE);
  private final EventLoopGroup group = new NioEventLoopGroup();
  private Channel channel;

  public ChannelFuture start(InetSocketAddress address) {
    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(group)
        .channel(NioServerSocketChannel.class)
        .childHandler(createInitializer(channelGroup));
    ChannelFuture future = bootstrap.bind(address);
    future.syncUninterruptibly();
    channel = future.channel();
    return future;
  }

  private ChannelInitializer<Channel> createInitializer(ChannelGroup channelGroup) {
    return new ChatServerInitializer(channelGroup);
  }

  private void destroy() {
    if (channel != null) {
      channel.close();
    }
    channelGroup.close();
    group.shutdownGracefully();
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      log.error("please give port as argument");
      System.exit(1);
    }
    int port = Integer.parseInt(args[0]);
    final ChatServer endpoint = new ChatServer();
    ChannelFuture future = endpoint.start(new InetSocketAddress(port));

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        endpoint.destroy();
      }
    });
    future.channel().closeFuture().syncUninterruptibly();
  }

}
