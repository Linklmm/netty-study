package com.lmm.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-06-04 16:04
 * @description: fileChannel相关代码
 **/
@Slf4j
public class FileChannelDemo {

  public static void main(String[] args) throws IOException {
    //创建fileChannel
    RandomAccessFile accessFile = new RandomAccessFile(
        "/Users/playboy/work/java/workSpace/playboy/netty-study/netty-action-01/src/main/resources/2.txt",
        "rw");
    FileChannel channel = accessFile.getChannel();
    //创建Buffer
    ByteBuffer buf = ByteBuffer.allocate(16);

    //读取数据到Buffer中
    int byteRead = channel.read(buf);
    while (byteRead != -1) {
      log.error("读取了:{}", byteRead);
      buf.flip();
      while (buf.hasRemaining()) {
        log.error("{}", (char) buf.get());
      }
      buf.clear();
      byteRead = channel.read(buf);
    }
    accessFile.close();
    log.error("读取文件结束");
  }

}
