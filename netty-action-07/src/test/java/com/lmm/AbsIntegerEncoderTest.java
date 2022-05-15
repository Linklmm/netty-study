package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-08 16:56
 * @description:
 **/
public class AbsIntegerEncoderTest {

  @Test
  public void testEncoded() {
    ByteBuf buf = Unpooled.buffer();
    for (int i = 1; i < 10; i++) {
      buf.writeInt(i * -1);
    }

    EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
    Assert.assertTrue(channel.writeOutbound(buf));
    Assert.assertTrue(channel.finish());

    for (int i = 1; i < 10; i++) {
      Assert.assertEquals(i, channel.readOutbound());
    }

    Assert.assertNull(channel.readOutbound());
  }

}