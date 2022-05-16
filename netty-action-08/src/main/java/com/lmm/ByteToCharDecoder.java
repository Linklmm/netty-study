package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-16 22:26
 * @description: 扩展ByteToMessageDecoder
 **/
public class ByteToCharDecoder extends ByteToMessageDecoder {

  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    while (in.readableBytes() >= 2) {
      out.add(in.readChar());
    }
  }
}
