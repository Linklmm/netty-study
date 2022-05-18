package com.lmm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-18 21:30
 * @description: 在服务器端支持WebSocket
 **/
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

  protected void initChannel(Channel ch) throws Exception {
    ch.pipeline().addLast(
        new HttpServerCodec(),
        //为握手提供聚合的HttpRequest
        new HttpObjectAggregator(65536),
        //如果被请求的端点是"/websocket"则处理该升级握手
        new WebSocketServerProtocolHandler("/websocket"),
        new TextFrameHandler(),
        new BinaryFrameHandler(),
        new ContinuationFrameHandler()
    );
  }

  public static final class TextFrameHandler extends
      SimpleChannelInboundHandler<TextWebSocketFrame> {

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
        throws Exception {
      //handler text frame

    }
  }

  public static final class BinaryFrameHandler extends
      SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg)
        throws Exception {

    }
  }

  public static final class ContinuationFrameHandler extends
      SimpleChannelInboundHandler<ContinuationWebSocketFrame> {

    protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg)
        throws Exception {

    }
  }

}
