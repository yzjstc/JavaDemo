package com.demo.scala.concurent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.Future

object BlockDemo {
  //阻塞式多线程
  def main(args: Array[String]): Unit = {
    val fu = Future {
      println("线程运行起来了!");
      Thread.sleep(1000);
      999;
    }
    println("main......")
    val r = Await.result(fu, Duration.Inf);//阻塞时,再回到执行完成
    println(r);
  }
}