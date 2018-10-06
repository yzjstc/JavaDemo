package com.demo.scala

object Demo01 {
  //main方法是入口,只有在object单例对象中的main方法才是入口,class类中定义main方法不是入口
  //另外,还可以通过继承Application接口来作为入口
  /*
   * 继承Application作为入口有副作用:
   * 1.无法接受命令行中的args参数,因为args参数不会被传入
   * 2.在scala中,如果一个程序是多线程的,那该程序必须具有一个main方法,所以第二种写法只适用于单线程
   * 3.Application这个接口在执行一个程序的代码之前,会进行一些初始化,而有些jvm不会对这些初始化代码进行优化
   * 一般都不用Application,了解下,然后忘了它
   */
  def main(args: Array[String]): Unit = {
//    val str1 = "abcdefg";
//    val c1 = str1.charAt(1);//scala是完全兼容java的,所以原有的API依然可以在scala中使用
//    println(c1);
//    
    //""和"""""",差别在于,""""""就是原格式输出,""需要用转义字符,好像和python有点像哦
//    val str2 = "abc\r\n123\txyz\\ijk";
//    println(str2)
//    
//    val str3 = """abc
//123  xyz \ijk"""
//    println(str3)
//    
//    val a = 3 .*(2);//不吹不黑,运算符在scala竟然可以当函数看待!!!
//    println(a);
//    
//    val str4 = str1 toUpperCase();//调用函数除了应用.调用之外,还可以通过后缀操作符操作
//    println(str4);
//    
//    //if判断
//    val num = 99;
//    val r1 = if(num < 10){
//      "哈哈";
//    }else if(num < 100){
//      "嘻嘻";
//    }else{
//      "呵呵";
//    }
//    println(r1);
    
    //while和do...while
//    var i = 0;
//    var sum = 0;
//    while (i <= 10){//while和do..while会使用到外界变量,都不可避免地破坏函数封闭性
//      sum += i;
//      i += 1;
//    }
//    println(sum)
    
    //所以一般我们都用递归来实现循环!
    //方法的定义:    def 方法名(变量名:变量类型 ...):返回值类型={statements}
//    def wLoop(i:Int,sum:Int):Int={
//      if(i > 10) return sum;
//      else wLoop(i+1,sum + i);
//    }
//    val r = wLoop(0,0);
//    println(r);
    
    //for循环
    //1.增强for循环
//    val ll = List(1,3,5,7,9);
//    for(n <- ll){
//      println(n);
//    }
    //2.普通for循环
//    for( i <- 1 to 10){
//      println("当前循环值为:" + i);
//    }
    //3.过滤for
//    for(i <- 0 to 100; if i % 2 == 0){//遍历偶数
//      println(i);
//    }
    //多个判断条件用;隔开
//    for(i <- 0 to 100;if i % 2 ==0;if i % 7 == 0;if i > 30){
//      println(i);
//    }
    //嵌套for循环
//    for(i <- 1 to 9){
//      for(j <- 1 to i){
//        print(j + "*" + i + "=" + i*j +" ")
//      }
//    }
    println();
    //可以写到一个for循环中
//    for(i <- 1 to 9; j <- 1 to i){
//    	print(j + "*" + i + "=" + i*j +" ")
//    }
    
    //流间变量绑定
//    for(i <- 1 to 9; j <- 1 to i; val str = "\r\n"){
//      print(j + "*" + i + "=" + i*j + str)
//    }
//    
//    for(i <- 1 to 9; j <- 1 to i; val str = if(i == j )"\r\n" else " "){
//    	print(j + "*" + i + "=" + i*j + str)
//    }
    //创建新的集合
    //由于for循环默认的返回值类型是Unit, 即不能带回返回值
    //但是可以通过yield关键字将每次循环的  最后一个表达式  的值组成集合返回,并且用,分隔开
//    var r = for(i <- 1 to 9; j <- 1 to i) yield {
//      i+ "+" +j + "=" + i+j;//该行被忽略了,没放到集合中
//      val str = j + "*" + i + "=" + i*j;//有赋值,若该句放到最后,则返回的集合中都是空白
//      str;//str作为返回值,放到集合中,没有这个,就返回空了
//    }
//    println(r)
    
    //try ... catch ... finally捕获异常
    //scala中继承了java的异常机制, 提供了程序中产生意外情况时处理的机制
    //抛出的异常的过程和java基本一致,通过throw关键字进行
//    try{
//      val i = 1/0;
//    }catch{
//      case t: NullPointerException => println("空指针异常");
//      case t: ArithmeticException => println("算数异常");
//      case t: RuntimeException => println("运行时异常");
//    }finally{
//      println("无论如何都会执行finally中的语句")
//    }
//    
//    val r = try{
//    	val i = 5/0;
//    	i;//如果这里不写i,直接就是赋值语句了,很危险!发现最后输出的时候,根本就是空值
//    }catch{
//      case t: NullPointerException => "空指针异常";
//      case t: ArithmeticException => "算数异常";
//      case t: RuntimeException => "运行时异常";
//    }finally{
//      /*
//       * "无论如何都会执行finally语句"
//       * 如果只写这句话,可以看到小黄色波浪线,提示该语句作为单独语句会什么也不干
//       * 换句话来说!try...catch...finally块中的确可以有返回值,但是不会改变try或catch块中的返回值的值
//       * 这和java是很不一样的!!!!使用时应当注意!!!!!!
//       */
//      //可执行的函数,则会在返回try或catch块中的值前先执行
//      println("无论如何都会执行finally中的语句");
//    }
//    println(r);
    
    //match匹配
//    val str = "def";
//    var r = str match{
//      case "abc" => "哈哈哈";
//      case "def" => "呵呵呵";
//      case "ghi" => "啦啦啦";
//    }
//    println(r);
    
    var x = 0;
    var sum = 0;
    var flag = true;
    while(x < 100 && flag){
      if(sum > 50){
        flag = false;//break
      }
      if(x % 2 == 0){
        //continue,scala没有continue和break,只能通过布尔类型的变量来控制循环条件
      }else{
        sum += x;
      }
      x += 1;
    }
    println(sum)
    
    //但是这样从外部引入布尔类型的变量,有破坏了函数的特点同样不推荐. 通过递归的方式实现!
    def mx(i:Int,sum:Int):Int = {
      if (sum > 50 || i > 100) sum;
      else if (i % 2 == 0) mx(i+1,sum);
      else mx(i+1,sum+i);
    }
    val r = mx(0,0);
    println(r);
    
    //访问范围问题
    val str = "abc";
    if(3 > 2){
//      println(str);//报错,有外部的str,下面又有声明的str,这样是不可以的!!!
      val str = "xyz";
      println(str);//这样可以的
    }
    println(str);
  }
}






















