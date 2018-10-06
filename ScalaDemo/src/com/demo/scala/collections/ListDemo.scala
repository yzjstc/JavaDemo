package com.demo.scala.collections
import scala.collection.mutable.ArrayBuffer;

object ListDemo {
  def main(args: Array[String]): Unit = {
    /**
     * List列表
     * 包含类型相同,长度固定,内容不可变的数据的数据结构,底层用链表实现
     * 定长列表: scala.collection.immutable.List 一旦声明之后,长度不可变
     * 变长列表: scala.collection.mutable.ListBuffer, 变长列表
     */
    
    //定义list
    val l1 = List(1,3,5,7,9);
    l1.foreach { print(_); }
    println()
    val l2 = 2 :: 4 :: 6 :: 8 :: 10 :: Nil;
    l2.foreach {  print(_); }
    
    //获取数据
    println(l2(2));
    
    //遍历List
    //迭代遍历
    for (n <- l2){
      println(n)
    }
    //for循环遍历
    for(i <- 0 to l2.size-1){
    	println(l2(i));
    }
    //自带foreach方法遍历
    l2.foreach {  print(_); }

    //连接列表
    val l3 = l1 ::: l2;//该方法只能用于连接List类型的集合
//    val l3 = l1 ++ l2;
    println("\r\nl3遍历输出:")
    l3.foreach {  x => print(x+" "); }
    
    //插入元素
    println("\r\nl4遍历输出:")
    val l4 = 0 +: l3 :+ 10;//往头尾添加元素
    val l5 = l3 :+ 12;//往尾部添加
    val l6 = -1 +: l3;//往头部添加
    l4.foreach {  x => print(x+" "); }
    println("\r\nl5遍历输出:")
    l5.foreach {  x => print(x+" "); }
    println("\r\nl6遍历输出:")
    l6.foreach {  x => print(x+" "); }
    println("\r\n:+ 向尾部添加的运算方式可以添加多个元素,结果遍历输出:")
    (l6 :+ 16 :+ 17 ).foreach {  x => print(x+" "); }
   
    //删除元素
    val l7 = 2 :: 4 :: 6 :: 8 :: 10 :: 12 :: 14 :: 16 :: Nil;
    val l8 = l7.drop(2);//删除列表左边两个元素
    val l9 = l7.dropRight(2);//删除列表右边两个元素
    println("\r\nl8遍历输出:")
    l8.foreach {  x => print(x+" "); }
    println("\r\nl9遍历输出:")
    l9.foreach {  x => print(x+" "); }

    //修改元素
    val l10 = l7.updated(2, 3);//将下标为3的元素替换成3
    println("\r\nl10遍历输出:")
    l10.foreach {  x => print(x+" "); }
    
    /*
     * attention!!!!!
     * List本身无法修改,以上的包括 插入 删除 修改操作,都没有改变原有的List,而是产生了新的List返回.
     */
    
    //bufferDemo
    val buf = ArrayBuffer(1,3,5,7,9);
    buf += 11
    val arr = buf.toArray;//转换为array
    arr.foreach(println(_))
    buf.clear();
    
    /**
     * Range
     * 代表一个数据的范围,常见于for循环的方位限定
     */
    val r1 = 1 to 100;
    r1.foreach { println(_) }
    for(i <- 0 to 10){
      println("a"+i)
    }
    
    /**
     * Vector
     * Scala2.8开始为了提高list的随机存取效率而引入的新集合类型
     * （而list存取前部的元素快，越往后越慢）
     */
    val v1 = Vector(1,3,5);
    v1.foreach(println(_));
    val v2 = 0 +: v1 :+ 6;
    v2.foreach(println(_));
    
    /**
     * Set
     * 无序无重复的集合
     */
    val s1 = Set(1,3,5,7,9,3,6,9,10)
    s1.foreach(println(_))
    
  }
}