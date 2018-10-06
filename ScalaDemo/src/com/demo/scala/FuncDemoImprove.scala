package com.demo.scala

object FuncDemoImprove {
  def cook(food1:String,food2:String,howToCook:(String,String)=>String)={
    howToCook(food1,food2);
  }
  
  def sum(n1:Int,n2:Int,n3:Int):Int={
    return n1 + n2 * n3;
  }
  def main(args: Array[String]): Unit = {
	  //使用 _ 占位符,简化代码
	  /*
	   * 使用占位符,替代函数直接量的参数声明
	   * 当函数直接量中的所有参数,都只在函数体中使用过一次,且严格按声明顺序使用,则可以使用 _ 替代这些参数
	   */
	  val r1 = cook("羊肉串","孜然","烤"+_+"要加"+_);
	  println(r1)
	  //此处无法使用 _ 占位符来简化,因为使用顺序不能按照顺序使用
    val r2 = cook("羊肉串","孜然",(f1,f2)=>{"撒"+f2+"烤"+f1});
	  println(r2)
	  
	  //使用占位符,替代函数值用法中的部分或全部参数列表
	  val sum2 = sum(_,_,_);
	  val r3 = sum2(1,2,3);
	  println(r3);
	  val sum3 = sum(_:Int,_:Int,10);//需要对参数类型进行指定,且需和声明时的参数类型一致
	  val r4 = sum3(1,2);
	  println(r4)
	  
	  val r5 = sumx(5050,1,2,3,4);
	  println(r5)
	  
	  addAndPrint2(2, 3){
	    println(_)
	  }
  }
  
  /*
   * 重复参数:
   * 类似于java中的可变参数,可以将最后一个参数设置为重复参数,来接收0到多个该类型的参数值
   * 在方法内部可以按照类似数组方式访问这些参数
   */
  def sumx(init:Int,nums:Int*):Int={
    var sum = init;
    for(n <- nums){
      sum =sum + n;
    }
    return sum;
  }
  
  /*
   * 尾递归:
   * 在递归时,保证函数每次递归在函数体的最后一部操作才是在递归调用自己,则称这样的递归为尾递归
   * 尾递归效率更高,应该尽力去实现尾递归
   */
  //尾递归写法范例
  def sumd(i:Int,sum:Int):Int={
    if(sum > 100 || i > 50) sum;//返回sum
    else if(i % 2 != 0){
      println("本次递归")
      sumd(i+1,sum+i);//进入循环,函数体最后一部,调用自身函数
    }else{
      sumd(i+1,sum);//进入循环,函数体最后一部,调用自身函数
    }
  }
  
  /*
   * 闭包问题:
   * 函数应该是一个尽量 封闭 独立 可靠 的结构,而当函数使用了外部变量时,会造成函数本身没有完成封闭
   * 直到函数被调用的时候, 真正要执行时,才去将外部变量的值获取到,以用来封闭函数,执行代码
   * 这个过程称之为闭包的过程
   * 可见,闭包不是一种技术,而是一个现象,一个程序编写的现象
   * 闭包这种现象在很多时候都是有害的,体现在:
   * 外部变量的变化造成程序执行时的不稳定,以及在使用高阶函数时,引用外部环境变量,造成整个环境无法被释放,造成内存泄漏的问题
   * 所以在函数式编程的开发过程中,应当充分考虑闭包可能带来的危害,尽力去避免
   * 例如循环时,尽量不使用外部变量,而是用递归
   */
  
  /**
   * 柯里化 - 自定义控制结构
   * 所谓柯里化,就是一个函数的参数列表,拆分成多个参数列表的过程
   */
  def addAndPrint(x:Int,y:Int,z:(Int)=>Unit) = {
    val sum = x+y
    z(sum)
  }
  def addAndPrint2(x:Int,y:Int)(z:(Int)=>Unit) = {
    val sum = x+y
    z(sum)
  } 
  def addAndPrint3(x:Int)(y:Int)(z:(Int)=>Unit) = {
	  val sum = x+y
		z(sum)
  } 
  /*
   * 自定义控制结构:
   * 在scala中并没有提供太多的内置控制结构,而是以够用为标准提供了最基本的控制结构
   * 可以在scala中 通过 高阶函数 + 柯里化 实现自定义控制结构
   * 在函数的参数列表中,最后一项为高阶函数,通过柯里化,将最后一个参数独立为一个单独的参数列表
   * 在调用的时候,单独传入高阶函数,而最后一个参数列表中只有一个参数,可以将包裹它的小括号写成大括号
   * 则整个函数看起来就像一个自定义的控制结构
   * 
   * addAndPrint2(2, 3){
	    println(_)
	  }
   */
  
}