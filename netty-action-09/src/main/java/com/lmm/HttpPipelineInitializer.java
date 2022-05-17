package com.lmm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-17 20:42
 * @description: 添加HTTP支持
 **/
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

  private final boolean client;

  public HttpPipelineInitializer(boolean client) {
    this.client = client;
  }

  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //客户端处理
    if (client) {
      pipeline.addLast("decoder", new HttpResponseDecoder());
      //客户端添加 HttpRequestEncoder 以向客户端发送响应
      pipeline.addLast("encoder", new HttpRequestEncoder());
    } else {
      //接收客户端的请求
      pipeline.addLast("decoder", new HttpRequestDecoder());
      //服务器，向客户端发送响应
      pipeline.addLast("encoder", new HttpResponseEncoder());
    }
  }
}
