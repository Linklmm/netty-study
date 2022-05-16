package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-16 22:28
 * @description: 扩展MessageToByteEncoder
 **/
public class CharToByteEncoder extends MessageToByteEncoder<Character> {

  protected void encode(ChannelHandlerContext ctx, Character msg, ByteBuf out) throws Exception {
    out.writeChar(msg);
  }
}
