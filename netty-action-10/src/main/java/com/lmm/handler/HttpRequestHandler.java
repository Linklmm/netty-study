package com.lmm.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-22 14:54
 * @description: HTTP请求处理器
 **/
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  private final String wsUri;
  private static final File INDEX;

  static {
    URL location = HttpRequestHandler.class.getProtectionDomain()
        .getCodeSource().getLocation();
    String path = null;
    try {
      path = location.toURI() + "index.html";
    } catch (URISyntaxException e) {
      throw new IllegalStateException("Unable to locate index.html", e);
    }
    path = !path.contains("file:") ? path : path.substring(5);
    INDEX = new File(path);
  }

  public HttpRequestHandler(String wsUri) {
    this.wsUri = wsUri;
  }

  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
    //如果请求了WebSocket协议升级，则增加引用计数（调用retain（）方法），
    // 并将它传递给下一个ChannelInboundHandler
    if (wsUri.equalsIgnoreCase(request.uri())) {
      ctx.fireChannelRead(request.retain());
    } else {
      //处理100的Continue请求以符合HTTP1.1规范
      if (HttpUtil.is100ContinueExpected(request)) {
        send100Continue(ctx);
      }
      //读取index.html
      RandomAccessFile file = new RandomAccessFile(INDEX, "r");
      HttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(),
          HttpResponseStatus.OK);
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
      boolean keepAlive = HttpUtil.isKeepAlive(request);
      if (keepAlive) {
        //如果请求了keep-alive，则添加所需要的HTTP头信息
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
      }
      //将HttpResponse写到客户端
      ctx.write(response);
      if (ctx.pipeline().get(SslHandler.class) == null) {
        //将index.html写到客户端
        ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
      } else {
        ctx.write(new ChunkedNioFile(file.getChannel()));
      }
      //写LastHttpContent并冲刷至客户端
      ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
      if (!keepAlive) {
        //如果没有请求keep-alive，则在写操作完成后关闭Channel。
        future.addListener(ChannelFutureListener.CLOSE);
      }
    }
  }

  private static void send100Continue(ChannelHandlerContext ctx) {
    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
        HttpResponseStatus.CONTINUE);
    ctx.writeAndFlush(response);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ;
    ctx.close();
  }
}
