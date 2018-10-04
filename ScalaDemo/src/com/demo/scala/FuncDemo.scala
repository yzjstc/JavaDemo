package com.demo.scala

object funcdemo {
  def main(args: Array[String]): Unit = {
    //函数声明:函数在scala中是一等公民,具有完整的功能
    //格式如下:
    //[override][final][public/private/protected] def 函数名(参数列表):返回值声明 = {函数体}
    
    //访问权限没有public,不写默认就是public
    def sum1(n1:Int,n2:Int):Int = {
      return n1 + n2;
    }
    println(sum1(1,2))

    def sum2(n1:Int,n2:Int):Int = {
      n1 + n2;//return可以省略,如果省略,函数会将最后一个表达式的值作为返回值返回
    }
    
    //在大部分情况下,函数可以自动推断返回值的类型,所以返回值类型的声明可以省略
    def sum3(n1:Int,n2:Int)={
      n1+n2;
    }
    //如果返回值的类型是Unit,则 :Unit= 可以省略
    def sum4(n1:Int,n2:Int){
      println(n1 + n2);
    }
    
    //函数定义的另一方式: 函数直接量定义
    /*
     * (参数列表)=>{函数体}
     */
    //可以直接使用函数直接量快速定义一个函数,= 前是函数名, => 是函数体
    val sum5 = (n1:Int,n2:Int) => {n1 + n2};
    println(sum5(1,2));
  
    //如果函数体只有一行内容, 函数体的大括号也可以省
    val sum6 = (n1:Int,n2:Int) => n1 + n2;
    println(sum6(1,2));
    
    //如果函数的参数类型可以自动推断, 那么函数参数类型声明可以省略
//    val sum7 = (n1,n2) => n1+n2;//此处报错,倒不是写法有问题,而是没有办法推断参数类型,所以报错
    
  }
}
















