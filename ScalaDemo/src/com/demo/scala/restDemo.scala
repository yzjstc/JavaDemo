package com.demo.scala

object restDemo {
 /**
  * 泛型,scala中的泛型和java中的泛型基本一致,java使用<>括起泛型,而scala使用[]括起泛型
  * 例如:
  * val arr = Array[String]();
  */
  
  //lazy,懒加载 || 慢执行
  /**
   * 正常情况下,通过val和var定义的属性都会直接分配空间,即使这个属性很久都不被使用,但是这样会造成内存空间被占用,而造成浪费
   * 这种情况下可以加上lazy关键字,延后变量/常量赋值的位置,这样知道后续真正用到这个量时,才会真正开辟内存空间并赋值,减少了内存的浪费
   */
  lazy val str = "aaaaa";
  
  /**
   * option
   * 在scala中用来表示一个结果,它可能有值,也可能没有值,它有两个子Option
   * Some(x)
   * None
   * 通过将函数的返回值设定为Option,可以通过Some带回正常值,通过None表示非正常的值,从而减少异常机制的使用,简化程序
   * 返回的Option结果可以调用.getOrElse(默认值),当返回的是Some时,将得到正常结果值
   * 如果返回的是None, 将得到该方法中的默认值
   */
  def div(n1:Int,n2:Int):Option[Int] = {
    if(n2 == 0){
      None
    }else{
      Some(n1/n2)
    }
  }
  def main(args: Array[String]): Unit = {
    val r = div(3,0).getOrElse(0);
    println(r)
    val p = PersonCase();
  }
  
  /**
   * case class 样例类
   * 只要在声明类时,在class关键字前加上case关键字,类名后面加(),这个类就成为了样例类,样例类和普通类的区别在于:
   * 默认实现序列化接口
   * 默认自动覆盖toString,equals,hashCode方法
   * 不需要new 就可以直接生成对象
   */
  //通常用在声明JavaBean
  case class PersonCase(){
    
  }
}