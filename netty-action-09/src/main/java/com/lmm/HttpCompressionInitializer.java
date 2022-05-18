package com.lmm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-18 20:52
 * @description: 自动压缩HTTP消息
 **/
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {

  private final boolean isClient;

  public HttpCompressionInitializer(boolean isClient) {
    this.isClient = isClient;
  }

  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    if (isClient) {
      //如果是客户端，则添加 HttpClientCodec
      pipeline.addLast("codec", new HttpClientCodec());
      //如果是客户端，则添加 HttpContentDecompressor 以处理来自服务器的压缩内容
      pipeline.addLast("decompressor", new HttpContentDecompressor());
    } else {
      pipeline.addLast("codec", new HttpServerCodec());
      pipeline.addLast("compressor", new HttpContentCompressor());
    }
  }
}
