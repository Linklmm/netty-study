package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-04-18 19:46
 * @description: 派生获取区
 **/
@Slf4j
public class TestByteBuf {

  Charset utf8 = Charset.forName("utf-8");
  ByteBuf buf = Unpooled.copiedBuffer("netty in Action rocks!", utf8);

  @Test
  public void testSlice() {

    log.error("init buf:{},buf:{}", buf.toString(utf8), buf);
    //共享源实例
    ByteBuf sliced = buf.slice(0, 5);
    log.error("=============slice=============");
    //源buf 的读写索引以及容量没有发生变化
    log.error("buf.slice:{},buf:{}", buf.toString(utf8), buf);
    //写索引直到 5
    log.error("sliced:{},sliced:{}", sliced.toString(utf8), sliced);

    buf.setByte(0, (byte) 'j');
    log.error("============change buf==========");
    log.error("sliced:{}", sliced.toString(utf8));
    log.error("buf:{}", buf.toString(utf8));

    //改变sliced 生成的实例
    sliced.setByte(0, (byte) 'N');
    log.error("=============change sliced============");
    log.error("sliced:{}", sliced.toString(utf8));
    log.error("buf:{}", buf.toString(utf8));
  }

  @Test
  public void testCopy() {
    ByteBuf copy = buf.copy(0, 15);
    log.error("========init========");
    log.error("buf.toString:{},buf:{}", buf.toString(utf8), buf);
    log.error("copy.toString:{},copy:{}", copy.toString(utf8), copy);

    buf.setByte(0, (byte) 'j');
    log.error("============change buf==========");
    log.error("copy.toString:{},copy:{}", copy.toString(utf8), copy);
    log.error("buf:{}", buf.toString(utf8));

    //两者互不影响，深拷贝
    copy.setByte(0, (byte) 'N');
    log.error("=============change sliced============");
    log.error("copy:{}", copy.toString(utf8));
    log.error("buf:{}", buf.toString(utf8));
  }

  @Test
  public void testGetAndSet() {
    log.error("buf first index:{}", (char) buf.getByte(0));
    //查看当前的读写索引位置
    log.error("rIndex:{},wIndex:{}", buf.readerIndex(), buf.writerIndex());
    buf.setByte(0, (byte) 'B');
    log.error("===========set byte=========");
    log.error("buf first index:{}", (char) buf.getByte(0));
    log.error("rIndex:{},wIndex:{}", buf.readerIndex(), buf.writerIndex());
  }

  @Test
  public void testReadAndWrite() {
    log.error("buf:{}", buf);
    log.error("=======read byte=========");
    //返回当前readerIndex 处的字节，并将readerIndex加1
    log.error("readByte:{}", (char) buf.readByte());
    log.error("rIndex:{},wIndex:{}", buf.readerIndex(), buf.writerIndex());

    buf.writeByte((byte) '?');
    log.error("===========write byte=========");
    log.error("buf first index:{}", (char) buf.getByte(0));
    log.error("rIndex:{},wIndex:{}", buf.readerIndex(), buf.writerIndex());
    //还是可以读到可丢弃字节的字节
    log.error("buf first index:{}", (char) buf.getByte(0));
    log.error("=========other method =========");
    //可被读取的字节数 windex-rIndex
    log.error("readableBytes:{}", buf.readableBytes());
    //可被写入的字节数cap-wIndex
    log.error("writableBytes:{}", buf.writableBytes());
  }


}
