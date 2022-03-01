package com.lmm.aio.handler;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ChannelHandler {

  private AsynchronousSocketChannel channel;
  private Charset charset;

  public void writeAndFlush(Object msg) {
    byte[] bytes = msg.toString().getBytes(charset);
    ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
    writeBuffer.put(bytes);
    writeBuffer.flip();
    channel.write(writeBuffer);
  }

}
