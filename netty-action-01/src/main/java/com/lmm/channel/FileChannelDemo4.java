package com.lmm.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-06-04 16:04
 * @description: fileChannel写相关代码 transferTo 方法演示，两个通道之间进行传输
 **/
@Slf4j
public class FileChannelDemo4 {

  public static void main(String[] args) throws IOException {
    //创建fileChannel
    RandomAccessFile fFile = new RandomAccessFile(
        "/Users/playboy/work/java/workSpace/playboy/netty-study/netty-action-01/src/main/resources/2.txt",
        "rw");
    FileChannel fChannel = fFile.getChannel();
    RandomAccessFile tFile = new RandomAccessFile(
        "/Users/playboy/work/java/workSpace/playboy/netty-study/netty-action-01/src/main/resources/4.txt",
        "rw");
    FileChannel tChannel = tFile.getChannel();

    fChannel.transferTo(0, fChannel.size(), tChannel);

    fFile.close();
    tFile.close();
  }

}
