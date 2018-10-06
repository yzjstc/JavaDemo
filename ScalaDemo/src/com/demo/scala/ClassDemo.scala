package com.demo.scala
//scala中的面向对象
class ClassDemo {
}

class Person4{
	/**
	 * 1.类的声明
	 * scala中的类基本和java中相同
	 * scala中的class关键字声明类
	 * scala中的类同样可以具有成员属性和成员方法
	 * scala中的类同样可以通过new关键字来创建对象
	 * scala中的类同样可以进行访问权限的控制,不同之处在于只有private和protected关键字,不写则 默认为public
	 */
	 private val name:String = "";
	 private val age:Int = 0;
	 def say(){
	   println("say something");
	 }
	 def eat(food:String){
	   println("want to eat" + food);
	 }
	 
	 //scala中没有static关键字,也就是说,scala类中不可以包含静态成员
	 //scala去掉静态后,针对类似于静态的使用要去,提出了单例对象的概念,模拟实现静态的效果
}

/**
 * 2.单例对象
 * 单例对象就是一个独立存在的对象,直接就是一个对象,不需要通过类来创建
 * 通过object关键字就可以声明一个单例对象
 * 单例对象,就是一个独立存在的对象,不需要new,直接就可以使用,来访问其中的属性和方法
 */
object Car{
  val name:String = "BMW";
  val speed:Int = 250;
  def run(){
    println(name + "时速是:" + speed);
  }
  def stop(){
    println("急刹车性能也不错");
  }
}

object DemoTest{
  def main(args:Array[String]) : Unit ={
    Car.run();
    Car.stop();
    println(Car.name + "||" + Car.speed);
    
    Person6.run();
    val p = new Person6();
    p.eat();
    p.say();
  }
}

/**
 * 单例对象可以单独存在,也可以和一个类产生伴生关系,这时候更像类中static资源
 * 只要将单例对象和要伴生的类声明在同一个scala文件中,且单例对象的名字和类名相同,就可以将单例对象和类伴生在一起
 * 类称之为该单例对象的伴生类,单例对象称之为该类的伴生对象
 * 伴生类和伴生对象之间可以相互访问对方的私有成员
 * 通过将单例对象和类产生伴生关系,可以在单例对象中增加方法和属性,用起来感觉就像类具有了静态成员
 */
class Person6{
  private val age = 19;
  def eat(){
    print(Person6.name + "巨能吃!");
  }
  def say(){
    println("还很能说!")
  }
}
object Person6{
  private val name = "张三";
  def run(){
    println("跑得老快了")
  }
}
