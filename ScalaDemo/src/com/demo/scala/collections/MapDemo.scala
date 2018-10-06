package com.demo.scala.collections

object MapDemo {
     /**
     * Map
     * Map存储键值对,键不可重复,且无序
     */
  def main(args: Array[String]): Unit = {
    //定义Map
    val m1 = Map(1->"aa",2->"bb",3->"cc",4->"dd",2->"ee",5->"ff");
    m1.foreach(println(_))
    
    //访问Map
    val m2 = Map(1->"aa",2->"bb",3->"cc",4->"dd",5->"ff");
    println(m2(3))
 
    //遍历 Map
    val m3 = Map[Int,String](1->"aa",2->"bb",3->"cc",4->"dd",5->"ff");
    for(kv <- m3.iterator){
    	println(kv._1,kv._2)
    }
    for(k <- m3.keys){
	    println(k,m3(k))
    }
    m3.foreach((t:(Int,String))=>{println(t._1+"~"+t._2)})
    
    //追加元素
    val m4 = Map[Int,String](1->"aa",2->"bb",3->"cc");
    val m5 = m4 + (4->"dd");
    m5.foreach((t:(Int,String))=>{println(t._1+"~"+t._2)})
    //删除元素
    val m6 = Map[Int,String](1->"aa",2->"bb",3->"cc");
    val m7 = m6 - 2;
    m7.foreach((t:(Int,String))=>{println(t._1+"~"+t._2)})

    // zip操作生成Map
    val l1 = List(1,2,3,4,5)
    val l2 = List("a","b","c","d","e")
    val m8 = l1.zip(l2).toMap;
    m8.foreach((t:(Int,String))=>{println(t._1+"~"+t._2)})
  }
}