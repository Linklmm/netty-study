package com.lmm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.SSLEngine;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-17 20:28
 * @description: 添加SSL/TLS支持
 **/
public class SslChannelInitializer extends ChannelInitializer<Channel> {

  private final SslContext sslContext;
  private final boolean startTls;

  /**
   * @param sslContext 传入需要使用的sslContext
   * @param startTls   如果设置为true，第一个写入的消息将不会被加密（客户端应该设置为true）
   */
  public SslChannelInitializer(SslContext sslContext, boolean startTls) {
    this.sslContext = sslContext;
    this.startTls = startTls;
  }

  protected void initChannel(Channel ch) throws Exception {
    //对于每个SslHandler实例，都使用Channel的ByteBufAllocator从SslContext获取一个新的SSLEngine
    SSLEngine engine = sslContext.newEngine(ch.alloc());
    ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
  }
}
