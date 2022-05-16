package com.lmm;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-15 16:41
 * @description: 将入站消息integer转成String
 **/
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {

  protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
    out.add(String.valueOf(msg));
  }
}
