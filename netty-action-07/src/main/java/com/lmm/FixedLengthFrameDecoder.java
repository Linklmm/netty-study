package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-08 16:33
 * @description: 测试入站消息
 **/
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

  private final int frameLength;

  public FixedLengthFrameDecoder(int frameLength) {
    if (frameLength <= 0) {
      throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength);
    }
    this.frameLength = frameLength;
  }


  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    while (in.readableBytes() >= frameLength) {
      ByteBuf buf = in.readBytes(frameLength);
      out.add(buf);
    }
  }
}
