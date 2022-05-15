package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-08 16:38
 * @description:
 **/
public class FixedLengthFrameDecoderTest {

  @Test
  public void testFramesDecode() {
    ByteBuf buf = Unpooled.buffer();
    //创建一个存储9个字节的ByteBuf
    for (int i = 0; i < 9; i++) {
      buf.writeByte(i);
    }
    ByteBuf input = buf.duplicate();
    //创建一个EmbeddedChannel，并添加一个 FixedLengthFrameDecoder，其将以3字节的帧长度被测试
    EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
    //将数据写入 EmbeddedChannel
    Assert.assertTrue(channel.writeInbound(input.retain()));
    //标记完成
    Assert.assertTrue(channel.finish());

    //读取所生成的消息，并且验证是否有3帧（切片），每帧都为3字节
    ByteBuf read = channel.readInbound();
    Assert.assertEquals(buf.readSlice(3), read);
    read.release();

    read = channel.readInbound();
    Assert.assertEquals(buf.readSlice(3), read);
    read.release();

    read = channel.readInbound();
    Assert.assertEquals(buf.readSlice(3), read);
    read.release();

    Assert.assertNull(channel.readInbound());
    buf.release();
  }

  @Test
  public void testFramesDecode2() {
    ByteBuf buf = Unpooled.buffer();
    //创建一个存储9个字节的ByteBuf
    for (int i = 0; i < 9; i++) {
      buf.writeByte(i);
    }
    ByteBuf input = buf.duplicate();
    //创建一个EmbeddedChannel，并添加一个 FixedLengthFrameDecoder，其将以3字节的帧长度被测试
    EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
    //返回 FALSE 因为不是一个完成的可读取帧
    Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
    Assert.assertTrue(channel.writeInbound(input.readBytes(7)));

    //标记完成
    Assert.assertTrue(channel.finish());

    //读取所生成的消息，并且验证是否有3帧（切片），每帧都为3字节
    ByteBuf read = channel.readInbound();
    Assert.assertEquals(buf.readSlice(3), read);
    read.release();

    read = channel.readInbound();
    Assert.assertEquals(buf.readSlice(3), read);
    read.release();

    read = channel.readInbound();
    Assert.assertEquals(buf.readSlice(3), read);
    read.release();

    Assert.assertNull(channel.readInbound());
    buf.release();
  }

}