package com.lmm.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Channel初始化
 */
@Slf4j
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) {
    log.info("链接报告开始");
    log.info("链接报告信息：有一客户端链接到本服务端");
    log.error("链接报告IP:{}", socketChannel.localAddress().getHostName());
    log.error("链接报告Port:{}", socketChannel.localAddress().getPort());
    log.info("链接报告完毕");
  }
}
