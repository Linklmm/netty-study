package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-08 17:03
 * @description:
 **/
@Slf4j
public class FrameChunkDecoderTest {

  @Test
  public void testFramesDecoded() {
    ByteBuf buf = Unpooled.buffer();
    for (int i = 0; i < 9; i++) {
      buf.writeByte(i);
    }
    ByteBuf input = buf.duplicate();

    EmbeddedChannel channel = new EmbeddedChannel(new
        FrameChunkDecoder(3));

    Assert.assertTrue(channel.writeInbound(input.readBytes(2)));

    try {
      channel.writeInbound(channel.writeInbound(input.readBytes(4)));
    } catch (TooLongFrameException e) {
      // TooLongFrameException
      log.error("TooLongFrameException");
    }

    //写入剩余的2字节
    Assert.assertTrue(channel.writeInbound(input.readBytes(3)));
    Assert.assertTrue(channel.finish());

    ByteBuf read = channel.readInbound();
    Assert.assertEquals(buf.readSlice(2), read);
    read.release();

    read = channel.readInbound();
    Assert.assertEquals(buf.skipBytes(4).readSlice(3), read);
    read.release();
    buf.release();
  }

}