package com.lmm.server.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-04-12 22:17
 * @description: 复合缓冲区
 **/
@Slf4j
public class CompositeByteBufTest {

  //使用ByteBuffer的复合缓冲区模式
  public static void main(String[] args) {
    ByteBuffer header = ByteBuffer.allocate(16);
    ByteBuffer body = ByteBuffer.allocate(16);
    ByteBuffer[] message = new ByteBuffer[]{header, body};
    ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
    message2.put(header);
    message2.put(body);
    message2.flip();
    log.error(message2.toString());
  }

  //使用CompositeByteBuf的复合缓冲区模式
  @Test
  public void testCompositeByteBuf() {
    CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
    ByteBuf header = Unpooled.copiedBuffer("我是头部", Charset.forName("utf-8"));
    ByteBuf body = Unpooled.copiedBuffer("我是主体2333", Charset.forName("utf-8"));
    compositeByteBuf.addComponents(header, body);

    //删除位于索引位置为0（第一个组件）的ByteBuf
    compositeByteBuf.removeComponent(0);
    for (ByteBuf buf : compositeByteBuf) {
      log.error(buf.toString());
    }
  }

  @Test
  public void testHeapByteBuf(){
    ByteBuf byteBuf = Unpooled.copiedBuffer("我是测试", Charset.forName("utf-8"));
    //堆缓冲区
    if (byteBuf.hasArray()) {
      byte[] array = byteBuf.array();
      //计算第一个字节的偏移量
      int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
      //获得可读字节数
      int length = byteBuf.readableBytes();
      log.error("堆缓冲区：offset:{},length:{}", offset, length);
    }
    //检查ByteBuf是否有数组支撑。如果不是，则这是一个直接缓冲区
    if (!byteBuf.hasArray()) {
      //获取可读字节数
      int length = byteBuf.readableBytes();
      //分配一个新的数组来保存具有该长度的字节数据
      byte[] array = new byte[length];
      //将字节复制到该数组
      byteBuf.getBytes(byteBuf.readerIndex(), array);
      log.error("直接缓冲区 length:{}", length);
    }
  }
}
