package com.lmm.schedule;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-study
 * @author: playboy
 * @create: 2022-05-02 14:58
 * @description: 使用ScheduledExecutorService调度任务
 **/
@Slf4j
public class TestScheduledExecutorService {

  public static void main(String[] args) throws InterruptedException {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    log.error("now:{}", LocalDateTime.now());
    try {
      ScheduledFuture<?> future = executorService
          .schedule(() -> {
            log.error("已经过去6s了");
            log.error("after:{}", LocalDateTime.now());
          }, 6, TimeUnit.SECONDS);
    } finally {
      executorService.shutdown();
    }

    Thread.sleep(10000);
  }

}
