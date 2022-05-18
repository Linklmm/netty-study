package com.lmm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.SSLEngine;
import lombok.AllArgsConstructor;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-18 20:57
 * @description: 使用HTTPS
 **/
@AllArgsConstructor
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {

  private final SslContext sslContext;
  private final boolean isClient;

  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    SSLEngine engine = sslContext.newEngine(ch.alloc());
    //将 SslHandler 添加到ChannelPipeline中以使用HTTPS
    pipeline.addFirst("ssl", new SslHandler(engine));
    if (isClient) {
      pipeline.addLast("codec", new HttpClientCodec());
    } else {
      pipeline.addLast("codec", new HttpServerCodec());
    }
  }
}
