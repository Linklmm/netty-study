package com.lmm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import java.io.Serializable;
import lombok.AllArgsConstructor;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-22 14:31
 * @description: 使用JBoss Marshalling
 **/
@AllArgsConstructor
public class MarshallingInitializer extends ChannelInitializer<Channel> {
  private final MarshallerProvider marshallerProvider;
  private final UnmarshallerProvider unmarshallerProvider;

  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //添加 MarshallingDecoder 将ByteBuf转换为POJO
    pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
    // 添加 MarshallingEncoder 将 pojo 转换为 ByteBuf
    pipeline.addLast(new MarshallingEncoder(marshallerProvider));
    //添加 ObjectHandler 处理实现了 Serializable 的pojo
    pipeline.addLast(new ObjectHandler());
  }

  public static final class ObjectHandler extends SimpleChannelInboundHandler<Serializable>{

    protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {

    }
  }
}
