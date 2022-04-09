package com.lmm.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-04-09 20:26
 * @description: netty NIO实现
 **/
public class NettyNioServer {
  public void server(int port) throws InterruptedException {
    final ByteBuf buf = Unpooled.copiedBuffer("hi,,我是服务端", Charset.forName("utf-8"));
    // nio 使用NioEventLoopGroup
    // oio的差别主要是一个调用了OioEventLoopGroup，一个是NioEventLoopGroup
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    //服务端引导类
    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(eventLoopGroup)
          .channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(
                  new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                      ctx.writeAndFlush(buf.duplicate())
                          .addListener(ChannelFutureListener.CLOSE);
                    }
                  });
            }
          });
      //绑定服务器以接受连接
      ChannelFuture channelFuture = serverBootstrap.bind().sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      //释放所有的资源
      eventLoopGroup.shutdownGracefully().sync();
    }
  }


  public static void main(String[] args) throws InterruptedException {
    NettyNioServer nettyNioServer = new NettyNioServer();
    nettyNioServer.server(9999);
  }
}
