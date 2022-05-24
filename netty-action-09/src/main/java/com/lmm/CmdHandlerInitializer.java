package com.lmm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-22 10:08
 * @description: 使用ChannelInitializer安装解码器
 * 1、传入数据流是一系列的帧，每个帧都由换行符(\n)分隔
 * 2、每个帧由一系列的元素组成，每个元素都由单个空格字符分隔；
 * 3、一个帧的内容代表一个命令，定义为一个命令名称后跟着数目可变的参数。
 *
 *
 * 1、Cmd——将帧（命令）的内容存储在ByteBuf中，一个ByteBuf用于名称，另一个用于参数；
 * 2、CmdDecoder——从被重写了的decode()方法中获取一行字符串，并从它的内容构建一个Cmd的实例；
 * 3、CmdHandler——从CmdDecoder获取解码的Cmd对象，并对它进行一些处理；
 * 4、CmdHandlerInitializer——为了简便起见，我们将会把前面的这些类定义为专门的ChannelInitializer的嵌套类，
 * 并将会把这些ChannelInBoundHandler安装到ChannelPipeline。
 **/
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {

  public static final byte SPACE = (byte) ' ';

  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //添加 CmdDecoder 以提取Cmd对象，并将它转发给下一个ChannelInboundHandler
    pipeline.addLast(new CmdDecoder(64 * 1024));
    //添加一个 CmdHandler 以接收和处理Cmd对象
    pipeline.addLast(new CmdHandler());
  }

  /**
   * cmd pojo对象
   */
  @AllArgsConstructor
  @Getter
  private static final class Cmd {

    private final ByteBuf name;
    private final ByteBuf args;
  }

  public static final class CmdDecoder extends LineBasedFrameDecoder {

    public CmdDecoder(int maxLength) {
      super(maxLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
      //从 ByteBuf中提取由行尾符序列分隔的帧
      ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
      if (frame == null) {
        return null;
      }
      //查找第一个空格字符的索引。前面是命令的名称，接着是参数
      int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), SPACE);
      //使用包含有命令名称和参数的切片创建新的Cmd对象。
      return new Cmd(frame.slice(frame.readerIndex(), index),
          frame.slice(index + 1, frame.writerIndex()));
    }
  }

  public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd> {

    protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {
      //处理传经ChannelPipeline的对象。
    }
  }
}
