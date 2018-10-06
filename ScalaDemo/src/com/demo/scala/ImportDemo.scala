package com.demo.scala
import java.io._;
import java.util.{Date,HashMap};//尽管该种引用可行,但是还是建议一行引入一个类
import java.text.{SimpleDateFormat => SDF};

object ImportDemo {
  /**
   * scala类似于java,可以通过import关键字引入外部包中的类
   * scala中的import可以出现在代码任何地方
   * scala中的import可以指定的是包和类
   * scala中的import可以重命名或隐藏一些被引用的成员
   */
  
  import java.util.ArrayList;
  def main(args: Array[String]): Unit = {
		  val date = new Date();
		  val sdf = new SDF("yyyy-MM-dd HH:mm:ss");//也就是对SimpleDateFormat进行了重命名
		  println(sdf.format(date))    
  }
  
  /*
   * 有意思的访问权限控制,但是由于其使用方式也是很灵活,有时也可能造成一些不必要的麻烦
   * 
   * scala的权限控制不同于java, 只有private,protected, 如果不写默认就是public
   * 但是public不是关键字,不可以直接使用
   * 同时,也可以在访问权限控制符之后通过中括号来提供额外的访问权限支持,实现更精细的访问权限控制
   * private [额外允许的包名] xxx
   * protected [额外允许的包名] xxx
   */
  
}