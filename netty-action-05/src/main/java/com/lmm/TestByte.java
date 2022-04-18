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
 * @description: 字节级操作
 **/
@Slf4j
public class TestByte {

  Charset utf8 = Charset.forName("utf-8");
  ByteBuf buf = Unpooled.copiedBuffer("netty in Action rocks!", utf8);

  /**
   * 测试随机访问索引
   */
  @Test
  public void testRandomByte() {
    for (int i = 0; i < buf.capacity(); i++) {
      byte b = buf.getByte(i);
      log.error("{}", (char) b);
    }
  }


}
