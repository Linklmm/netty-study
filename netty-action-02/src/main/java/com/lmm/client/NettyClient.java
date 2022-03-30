package com.lmm.client;

import com.lmm.hanlder.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {

  private final String host;
  private final int port;


  public NettyClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void start() throws InterruptedException {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap
          .group(group)
          .channel(NioSocketChannel.class)//客户端是 NioSocketChannel
          .remoteAddress(new InetSocketAddress(host, port))
          .handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast(new ClientHandler());
            }
          });
      //连接服务端
      ChannelFuture channelFuture = bootstrap.connect().sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }

  }

  public static void main(String[] args) throws InterruptedException {
    if (args.length != 2) {
      log.error("usage:{}" + NettyClient.class.getSimpleName());
      return;
    }

    String host = args[0];
    int port = Integer.parseInt(args[1]);
    new NettyClient(host, port).start();
  }
}
