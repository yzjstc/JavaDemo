package com.demo.scala.collections

object ArrayDemo {
  /*
   * Array数组
   * 包含类型相同,长度固定,内容可变的数据的数据结构,底层由数组实现
   * 定长数组: scala.collection.immutable.Array 一旦声明之后,长度不可变,和java的数组类似
   * 变长数组: scala.collection.mutable.ArrayBuffer 动态数组	和java的ArrayList类似
   */
  def main(args: Array[String]): Unit = {
    //定义Array
    val arr1 = new Array(3);
    
    val arr2 = Array("aaa","bbb","ccc");
    val arr2t = Array("111","222");
    
    //访问Array
    println(arr2(1))//下标从0开始,但是取值用()
    
    //遍历Array
    //1.使用迭代器
    for(str<-arr2){
      println(str);
    }
    //2.根据数组长度,利用for循环
    for(i <- 0 to arr2.length-1){
      println(arr2(i))
    }
    
    //修改
    arr2(1) = "eee";
    arr2.foreach { x => println(x) };//也可以用内置的foreach进行遍历
//    arr2.foreach { println(_) };//使用_进行简化
    
    //多维数组,3行4列的多维数组
//    val arr3 = Array.ofDim(3,4);
    
    //更多API操作
    val arr4 = Array.concat(arr2t , arr2);
    println(arr4)//array没有重写toString,所以必须遍历
    arr4.foreach { x => print( x+" " ) }
    
    /*
     * def concat[T]( xss: Array[T]* ): Array[T]
     * 连接所有阵列成一个数组。
     * def copy( src: AnyRef, srcPos: Int, dest: AnyRef, destPos: Int, length: Int ): Unit
     * 复制一个数组到另一个。相当于Java的System.arraycopy(src, srcPos, dest, destPos, length).
     * def empty[T]: Array[T]
     * 返回长度为0的数组
     * def iterate[T]( start: T, len: Int)( f: (T) => T ): Array[T]
     * 返回一个包含一个函数的重复应用到初始值的数组。
     * def ofDim[T]( n1: Int ): Array[T]
     * 创建数组给出的尺寸。
     * def ofDim[T]( n1: Int, n2: Int ): Array[Array[T]]
     * 创建了一个2维数组
     * def ofDim[T]( n1: Int, n2: Int, n3: Int ): Array[Array[Array[T]]]
     * 创建3维数组
     * def range( start: Int, end: Int, step: Int ): Array[Int]
     * 返回包含一些整数间隔等间隔值的数组。
     * def range( start: Int, end: Int ): Array[Int]
     * 返回包含的范围内增加整数序列的数组。
     * def tabulate[T]( n: Int )(f: (Int)=> T): Array[T]
     * 返回包含一个给定的函数的值超过从0开始的范围内的整数值的数组。
     * def tabulate[T]( n1: Int, n2: Int )( f: (Int, Int ) => T): Array[Array[T]]
     * 返回一个包含给定函数的值超过整数值从0开始范围的二维数组
     */
  }
}