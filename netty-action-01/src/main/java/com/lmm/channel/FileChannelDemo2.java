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
 * @description: fileChannel写相关代码
 **/
@Slf4j
public class FileChannelDemo2 {

  public static void main(String[] args) throws IOException {
    //创建fileChannel
    RandomAccessFile accessFile = new RandomAccessFile(
        "/Users/playboy/work/java/workSpace/playboy/netty-study/netty-action-01/src/main/resources/2.txt",
        "rw");
    FileChannel channel = accessFile.getChannel();
    //创建Buffer
    String data = "write data";
    ByteBuffer buf = ByteBuffer.allocate(data.length());
    buf.clear();

    //写数据到Buffer中
    buf.put(data.getBytes());
    buf.flip();
    while (buf.hasRemaining()) {
      channel.write(buf);
    }
    channel.close();
    accessFile.close();
    log.error("写文件结束");
  }

}
