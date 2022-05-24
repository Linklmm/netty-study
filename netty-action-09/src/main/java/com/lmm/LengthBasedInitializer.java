package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-22 10:45
 * @description: 使用LengthFieldBaseFrameDecoder解码器基于长度的协议
 **/
public class LengthBasedInitializer extends ChannelInitializer<Channel> {

  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //使用 LengthFieldBasedFrameDecoder 解码将帧长度编码到帧其实的前8个字节中的消息
    pipeline.addLast(new LengthFieldBasedFrameDecoder(64 * 1024, 0, 8));
    //添加 FrameHandler以处理每个帧
    pipeline.addLast(new FrameHandler());
  }

  public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
      //处理帧的数据
    }
  }
}
