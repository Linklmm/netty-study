package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import java.util.List;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-08 17:01
 * @description: 测试异常处理
 **/
public class FrameChunkDecoder extends ByteToMessageDecoder {

  private final int maxFrameSize;

  public FrameChunkDecoder(int maxFrameSize) {
    this.maxFrameSize = maxFrameSize;
  }


  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    int readableBytes = in.readableBytes();
    //如果帧太大抛出异常
    if (readableBytes > maxFrameSize) {
      in.clear();
      throw new TooLongFrameException();
    }
    ByteBuf buf = in.readBytes(readableBytes);
    out.add(buf);
  }
}
