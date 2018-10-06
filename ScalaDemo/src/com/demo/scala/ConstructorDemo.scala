package com.demo.scala

object ConstructorDemo {
  def main(args: Array[String]): Unit = {
    val p = new Person7("zs",12);
     p.say();
     p.eat();
    val p1 = new Person7("tx");
    val p2 = new Person7();
  }
}

/**
 * 主构造器:
 * scala中可以在类的声明过程中在类名后跟上小括号,指定类的构造函数参数
 * 而在类的内部,不归属于任何成员属性和成员方法的内容,都会被收集起来成为类的构造函数的体
 */
class Person7(name:String,age:Int){
  def say(){
    println(name + "巨能说!")
  }
  def eat(){
	  println("才" + age +"岁,就巨能吃")
  }
  println("Person7对象创建了,name:" + name + ",age:" +age );
  
  /**
   * scala中的类除了可以声明主构造器之外,还可以声明零个或多个辅助构造器
   * 可以通过def this() 来声明一个辅助构造器
   * 所有的辅助构造器的第一行代码,都要来调用在其之前声明的其他构造器,所以任意的辅助构造器,其实最终都会调用主构造器上 
   */
  def this(name:String){
    this(name,18);
    println("由辅助构造器1号创建的"+ name + "---永远18");
  }
  def this(){
	  this("构造器2号");
	  println("辅助构造器2号创建的"+ name +":被构造了");
  }
  
  /*
   * 辅助构造器的使用其实就是调用自身的主构造器或已存在的构造器
   * 在调用辅助构造器进行构造的时候,会一级级地网上调用,从最顶层的构造器开始执行,直到调用的辅助构造器执行结束
   */
  
}

