package com.demo.scala

object ExtendsDemo {
  def main(args: Array[String]): Unit = {
    
		  val p1 = new Student();
		  p1.eat();
		  p1.study();
		  
		  val p2 = new Student1("张三",18);
		  p2.eat();
		  p2.study();
		  
		  //多态,子类可以当做父类使用
		  val stu:Person = new Student();
  }
}

//和java类似,scala同样支持继承,而且也是单继承
//可以通过extends关键字来实现继承
class Person(name:String,age:Int){
  def eat(){
    println("很能吃!")
  }
  println("i am " + this.name + ", my age is " + this.age );
}

//在父类没有无参构造的时候,需要子类继承的过程中,明确传入父类的构造函数需要的参数
//但是这样写有一个明显的缺点,显然在构造子类对象的时候,参数都是给定了的,虽然一些存在默认的情况下,这也是个选择
class Student extends Person("zs",18){
  def study(){
	  println("学习也很刻苦")
  }
}

//可以基于子类构造参数进行传递,这时候参数不给定了
class Student1(name:String,age:Int) extends Person(name,age){
  def study(){
	  println("学习更刻苦")
  }
}

/**
 * 抽象类:
 * 类似于java,scala同样支持抽象类的使用
 * 抽象类可以包含普通方法和抽象方法,抽象类无法直接实例化,通常用来继承
 * 使用abstract修饰
 */
abstract class AbstractDemo{
  def eat();
  def say(){
    println("haha");
  }
}
/**
 * 重写和重载
 * 子类中可以覆盖父类的同名同参数的方法,这个过程称之为父类方法实现重写
 * 一个类的内部可以存在多个同名但不同参数的方法,这称之为重载
 * 
 * 重写需要通过override关键字来声明进行
 * 重载基本和java一样
 */
 class PersonNew extends AbstractDemo{
   //实现抽象方法,否则报错
   def eat(){
     println("巨能吃!!")
   }
   
   //重写
   override def say(){
     println("hehe");
   }

   //重载
   def say(something:String){
     println(something);
   }
 }
 
//final,scala中final关键字可以用于成员方法,类本身, 作用和java相同

/* 
 * scala内置继承结构
 * scala中所有的类都直接或者间接继承自Any,所以所有类都有以下方法:
 * 	final def == (that:Any):Boolean
 * 	final def != (that:Any):Boolean
 * 	def equals(that:Any):Boolean
 * 	def hashCode:Int
 * 	def toString:String
 * 
 * Any有两个直接子类,分别是AnyVal和AnyRef
 * AnyVal下又有九个子类,分别是以下九个类型,都无法通过new创建对象
 * Byte Short Int Long Float Double Char Boolean Unit
 * 
 * 而其他类型都继承自AnyRef ,AnyRef就像加重的object
 * 
 * 特殊类型: scala.Null ,只有一个值 null. 而scala.Null是所有AnyRef类型的子孙类
 * 特殊类型: scala.Nothing ,这个类型没有任何的值, 表示当某个方法向上抛出异常时,当前方法的返回值类型
 * scala.Nothing, 是所有类公共的子孙
 */

 /** 
  * 特质 - trait
  * scala中的特质,类似于java中的接口
  * 但是scala中的特质既可以包含抽象方法也可以包含非抽象方法
  * 
  * 一个类可以混入多种特质
  * 和抽象类相比,抽象类和特质都可以包含抽象方法和非抽象方法,但抽象类只能继承一个,而特质可以混入多个
  * 这并不意味着特质可以替代抽象类, 抽象类通常用于满足继承结构,而特质的混入意味着在当前类加入新的特性
  * 根据业务特点,合理使用抽象类和特质
  */
 
 abstract class PersonTrait{
   def eat(){
     println("吃吃吃");
   }
   def say();
 }
 
 abstract class StudentTrait extends PersonTrait{
   def say(){
     println("整天叽叽喳喳");
   }
   
   def study(){
     println("学生闷头学!");
   }
 }
 
 trait codeSkill{
   def code();
   def debugCode(){
     println("会调试代码")
   }
 }
 
 trait driveSkill{
   def drive(){
	   println("还会开车!")     
   }
 }
 
 //特性的混入使用with关键字
 class bachelor extends StudentTrait with codeSkill with driveSkill{
   def code(){
     println("写得一手好代码");
   }
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
