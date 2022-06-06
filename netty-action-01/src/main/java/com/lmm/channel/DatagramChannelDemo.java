package com.lmm.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-06-06 15:50
 * @description: DatagramChannel（UDP连接）相关代码
 **/
@Slf4j
public class DatagramChannelDemo {

  /**
   * 发送实现
   */
  @Test
  public void sendDatagram() throws IOException, InterruptedException {
    //打开 DatagramChannel
    DatagramChannel sendChannel = DatagramChannel.open();
    InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 9999);
    //发送
    while (true) {
      ByteBuffer buffer = ByteBuffer.wrap("发送数据".getBytes("utf-8"));
      sendChannel.send(buffer, sendAddress);
      log.error("发送完成");
      Thread.sleep(1000);
    }

  }

  @Test
  public void receiveDatagram() throws IOException {
    //打开 DatagramChannel
    DatagramChannel receiveChannel = DatagramChannel.open();

    InetSocketAddress receiveAddress = new InetSocketAddress(9999);
    //绑定
    receiveChannel.bind(receiveAddress);

    ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

    while (true) {
      receiveBuffer.clear();
      SocketAddress receive = receiveChannel.receive(receiveBuffer);
      receiveBuffer.flip();
      log.error(receive.toString());
      log.error("{}", StandardCharsets.UTF_8.decode(receiveBuffer));
    }
  }

}
