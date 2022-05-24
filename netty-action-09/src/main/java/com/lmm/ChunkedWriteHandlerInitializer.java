package com.lmm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;
import java.io.File;
import java.io.FileInputStream;
import lombok.AllArgsConstructor;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-22 14:11
 * @description: 使用ChunkedStream传输文件内容
 **/
@AllArgsConstructor
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {

  private final File file;
  private final SslContext sslContext;
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //将 SslHandler 添加到 ChannelPipeline
    pipeline.addLast(new SslHandler(sslContext.newEngine(ch.alloc())));
    // 添加 ChunkedWriteHandler 以处理作为ChunkedInput传入的数据
    pipeline.addLast(new ChunkedWriteHandler());
    //一旦连接建立，WriteStreamHandler 就开始写文件数据
    pipeline.addLast(new WriteStreamHandler());
  }

  public final class WriteStreamHandler extends ChannelInboundHandlerAdapter{

    /**
     * 当连接建立时，channelActive方法使用ChunkedInput写文件数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      super.channelActive(ctx);
      ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
    }
  }
}
