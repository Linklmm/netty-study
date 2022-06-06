package com.lmm.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-06-06 16:30
 * @description: Buffer相关代码
 **/
@Slf4j
public class BufferDemo1 {

  @Test
  public void bufferRead() throws IOException {
    //创建 fileChannel
    RandomAccessFile accessFile = new RandomAccessFile(
        "/Users/playboy/work/java/workSpace/playboy/netty-study/netty-action-01/src/main/resources/2.txt",
        "rw");

    final FileChannel channel = accessFile.getChannel();

    //创建buffer， 大小
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    //读
    int read = channel.read(buffer);

    while (read != -1) {
      //read 模式
      buffer.flip();

      while (buffer.hasRemaining()) {
        log.error("{}", (char) buffer.get());
      }
      buffer.clear();
      read = channel.read(buffer);
    }
    accessFile.close();
  }

  @Test
  public void buffer02() {
    //创建 buffer
    IntBuffer buffer = IntBuffer.allocate(8);

    //放buffer
    for (int i = 0; i < buffer.capacity(); i++) {
      int j = 2 * (i + 1);
      buffer.put(j);
    }

    buffer.flip();

    while (buffer.hasRemaining()) {
      final int i = buffer.get();
      log.error("i={}", i);
    }
  }

}
