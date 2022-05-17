package com.lmm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-17 20:54
 * @description: 自动聚合HTTP的消息
 **/
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

  private final boolean isClient;

  public HttpAggregatorInitializer(boolean isClient) {
    this.isClient = isClient;
  }

  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    if (isClient) {
      pipeline.addLast("codec", new HttpClientCodec());
    } else {
      pipeline.addLast("codec", new HttpServerCodec());
    }
    //将最大的消息大小设置为512kb的 HttpObjectAggregator 添加到ChannelPipeline
    pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
  }
}
