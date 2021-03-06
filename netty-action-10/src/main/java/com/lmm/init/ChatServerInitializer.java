package com.lmm.init;

import com.lmm.handler.HttpRequestHandler;
import com.lmm.handler.TextWebSocketFrameHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-22 15:30
 * @description: 初始化ChannelPipeline
 **/
public class ChatServerInitializer extends ChannelInitializer<Channel> {

  private final ChannelGroup group;

  public ChatServerInitializer(ChannelGroup group) {
    this.group = group;
  }

  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new HttpServerCodec());
    pipeline.addLast(new ChunkedWriteHandler());
    pipeline.addLast(new HttpObjectAggregator(64 * 1024));
    pipeline.addLast(new HttpRequestHandler("/ws"));
    pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
    pipeline.addLast(new TextWebSocketFrameHandler(group));
  }
}
