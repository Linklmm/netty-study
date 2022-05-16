package com.lmm;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-16 22:29
 * @description:
 **/
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder,
    CharToByteEncoder> {

  public CombinedByteCharCodec() {
    super(new ByteToCharDecoder(), new CharToByteEncoder());
  }
}
