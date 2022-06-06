package com.lmm.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-06-06 19:40
 * @description: Selector选择器代码相关
 **/
public class SelectorDemo {

  public static void main(String[] args) throws IOException {
    //创建Selector
    Selector selector = Selector.open();

    //通道
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    //非阻塞
    serverSocketChannel.configureBlocking(false);

    //绑定链接
    serverSocketChannel.bind(new InetSocketAddress(9999));

    //将通道注册到选择器上
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    //查询已经就绪通道操作
    final Set<SelectionKey> selectionKeys = selector.selectedKeys();

    //遍历集合
    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
    while (selectionKeyIterator.hasNext()) {
      final SelectionKey key = selectionKeyIterator.next();

      //判断key就绪状态操作
      if (key.isAcceptable()) {

      } else if (key.isConnectable()) {

      } else if (key.isReadable()) {

      } else if (key.isWritable()) {

      }
      selectionKeyIterator.remove();
    }

  }

}
