package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-08 16:53
 * @description: 测试出站消息，将4个字节的负整数转成绝对值
 **/
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

  protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    while (msg.readableBytes() >=4){
      int value = Math.abs(msg.readInt());
      out.add(value);
    }
  }
}
