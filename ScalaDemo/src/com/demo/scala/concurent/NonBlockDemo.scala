package com.demo.scala.concurent

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object NonBlockDemo {
  /**
   * scala中的多线程 -- 非阻塞式
   */
  def main(args: Array[String]): Unit = {
    /*
     * scala中的多线程开发通过Future对象来实现的
     */
    val fu = Future{
      println("线程跑起来了!")
      Thread.sleep(100);
      999;
      }
    
    println("main.....");
    //执行成功的线程返回值处理代码
    fu.onSuccess{
      case x => println(x)
    }
    
    Thread.sleep(1000);
  }
}