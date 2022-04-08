package com.lmm.server.jdk;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-04-08 21:29
 * @description: Jdk实现异步网络编程
 **/
@Slf4j
public class PlainNioServer {

  public void server(int port) throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    ServerSocket serverSocket = serverSocketChannel.socket();
    InetSocketAddress address = new InetSocketAddress(port);
    //绑定端口
    serverSocket.bind(address);
    //打开Selector来处理Channel
    Selector selector = Selector.open();
    //将ServerSocket注册到Socket以接受连接
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    //回复内容
    final ByteBuffer msg = ByteBuffer.wrap("hi! 我是服务器".getBytes());
    for (; ; ) {
      try {
        //等待需要处理的新事件;阻塞将一直持续到下一个传入事件。
        selector.select();
      } catch (IOException e) {
        log.error("selector happen exception,", e);
        break;
      }
      //获取所有接收事件的SelectionKey实例
      Set<SelectionKey> readyKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = readyKeys.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();
        try {
          //检查事件是否是一个新的已经就绪可以被接受的连接
          if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            //接受客户端，并将它注册到选择器
            client
                .register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
            log.error("accepted from client:{}", client);
          }
          //检查套接字是否已经准备好写数据
          if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            while (buffer.hasRemaining()) {
              //将数据写到已连接的客户端
              if (0 == client.write(buffer)) {
                break;
              }
            }
            //关闭连接
            client.close();
          }
        } catch (IOException e) {
          key.cancel();
          key.channel().close();
        }


      }
    }

  }

  public static void main(String[] args) throws IOException {
    PlainNioServer server = new PlainNioServer();
    server.server(9999);
  }

}
