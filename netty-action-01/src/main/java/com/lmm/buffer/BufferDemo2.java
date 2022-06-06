package com.lmm.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-06-06 17:06
 * @description: Buffer相关操作
 **/
@Slf4j
public class BufferDemo2 {

  private static final int start = 0;
  private static final int size = 1024;


  @Test
  public void buffer01() {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    for (int i = 0; i < buffer.capacity(); i++) {
      buffer.put((byte) i);
    }

    //创建子缓冲区
    buffer.position(3);
    buffer.limit(7);

    ByteBuffer slice = buffer.slice();

    //改变子缓冲内容
    for (int i = 0; i < slice.capacity(); i++) {
      byte b = slice.get(i);
      b *= 10;
      slice.put(i, b);

    }

    buffer.position(0);
    buffer.limit(buffer.capacity());
    while (buffer.hasRemaining()) {
      log.error("{}", buffer.get());
    }
  }

  /**
   * 只读缓冲区
   */
  @Test
  public void buffer02() {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    for (int i = 0; i < buffer.capacity(); i++) {
      buffer.put((byte) i);
    }

    //创建只读缓冲区
    ByteBuffer readBuffer = buffer.asReadOnlyBuffer();

    for (int i = 0; i < buffer.capacity(); i++) {
      byte b = buffer.get(i);
      b *= 10;
      buffer.put(i, b);
    }

    readBuffer.position(0);
    readBuffer.limit(buffer.capacity());

    while (readBuffer.remaining() > 0) {
      log.error("{}", readBuffer.get());
    }
  }

  //直接缓冲区
  @Test
  public void buffer03() throws IOException {
    String inFile = "/Users/playboy/work/java/workSpace/playboy/netty-study/netty-action-01/src/main/resources/1.txt";
    FileInputStream fileInputStream = new FileInputStream(inFile);
    FileChannel fileChannel = fileInputStream.getChannel();

    String outFile = "/Users/playboy/work/java/workSpace/playboy/netty-study/netty-action-01/src/main/resources/02.txt";
    FileOutputStream fileOutputStream = new FileOutputStream(outFile);
    FileChannel fOutChannel = fileOutputStream.getChannel();

    //创建直接缓冲区
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    while (true) {
      buffer.clear();
      int r = fileChannel.read(buffer);
      if (r == -1) {
        break;
      }
      buffer.flip();
      fOutChannel.write(buffer);
    }
  }

  /**
   * 内存映射
   */
  @Test
  public void testBuffer04() throws IOException {
    RandomAccessFile accessFile = new RandomAccessFile(
        "/Users/playboy/work/java/workSpace/playboy/netty-study/netty-action-01/src/main/resources/2.txt",
        "rw");

    FileChannel fileChannel = accessFile.getChannel();

    MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, start, size);

    mappedByteBuffer.put(0, (byte) 97);
    mappedByteBuffer.put(1023, (byte) 122);
    accessFile.close();
  }

}
