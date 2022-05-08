package com.lmm.schedule;

import io.netty.channel.Channel;
import io.netty.channel.ThreadPerChannelEventLoop;
import io.netty.channel.ThreadPerChannelEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-02 14:58
 * @description:
 **/
@Slf4j
public class TestEventLoop extends ThreadPerChannelEventLoopGroup{

  public static void main(String[] args) throws InterruptedException {
    ThreadPerChannelEventLoopGroup threadEventExecutor = new TestEventLoop();
    ThreadPerChannelEventLoop eventLoop = new ThreadPerChannelEventLoop(threadEventExecutor);

  }

}
