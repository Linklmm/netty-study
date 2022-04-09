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
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-04-09 20:06
 * @description: 使用netty实现阻塞Io
 **/
@Slf4j
public class NettyOioServer {

  public void server(int port) throws InterruptedException {
    final ByteBuf buf = Unpooled.unreleasableBuffer(
        Unpooled.copiedBuffer("Hi,我是服务端", Charset.forName("utf-8")));
    EventLoopGroup eventLoopGroup = new OioEventLoopGroup();
    try {
      // 创建ServerBootstrap
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      // 使用 OioEventLoopGroup 以允许阻塞模式（旧的I/O）
      serverBootstrap.group(eventLoopGroup)
          .channel(OioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          //指定 ChannelInitializer 对于每个已接受的连接都调用它
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(
                  //添加一个 ChannelInboundHandlerAdapter 以拦截和处理事件
                  new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                      log.error("与客户端建立连接");
                      //将消息写到客户端，并添加 ChannelFutureListener 以便消息一被写完就关闭连接
                      ctx.writeAndFlush(buf.duplicate())
                          .addListener(ChannelFutureListener.CLOSE);
                    }
                  }
              );
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
    NettyOioServer nettyOioServer = new NettyOioServer();
    nettyOioServer.server(9999);
  }

}
