package com.lmm.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

/**
 * 阻塞io示例
 */
@Slf4j
public class BIOExample {

  public void handler() {
    int portNumber = 8080;
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(portNumber);
      Socket clientSocket = serverSocket.accept();
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      String request, response;
      while ((request = in.readLine()) != null) {
        if ("done".equals(request)) {
          break;
        }
        response = request;
        log.error("response:{}", response);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
