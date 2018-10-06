package com.demo.scala
//函数的使用---成员方法
//一共四种用法，成员方法,本地方法,函数值,高阶函数
//scala将函数作为类的一个成员,就称为函数或称为类的成员方法
    
/**
 * 函数用法之 成员方法
 * 作为类的成员,称之为成员方法
 */
class Person01 {
  private val name:String = "";
  private val age:Int = 0;
  //方法作为类的一个成员,所以这是一个成员方法
  def say(){
    println("say something");
  }
  def eat(food : String){
    println("eat" + food);
  }  
}

/**
 * 函数用法之 本地方法
 * 在函数内部再定义的函数,称之为本地方法
 */
class Person02{
  private val name:String = "Vince";
  private val age:Int = 18;
  def say(){
    println("say something");
  }
  //返回值为Unit,所以=可以省略
  def eat(food : String){
    //返回值不是Unit了,需要省略,而返回值的类型可以推断,所以返回值类型并没有声明
    def cook(food : String)={
      "cooked " + food;
    }
    println("eat " + cook(food));
  }  
}

/**
 * 函数用法之 函数值
 * 将函数本身也理解为一个对象,而函数名是指向这个对象的一个引用,所以可以将函数任意赋值给其他引用
 */
class SumDemo{
  def sum(n1:Int,n2:Int):Int={
    return n1 + n2;
  }
  //函数名直接调用,然后赋值
  val sum2 = sum(_,_);
  var r1 = sum2(2,3);
  println("r1: " + r1);
  
  //也可以直接讲一个函数直接量赋值给引用,从而对改引用调用该函数
  val sum3 = (n1:Int,n2:Int) => n1 + n2;
  val r2 = sum3(2,3);
  println("r2: " + r2)
}

/**
 * !!!函数用法之 高阶函数
 * 在scala中,函数可以作为另一个函数参数传递,或作为另一个函数的返回值返回
 */
//高阶函数 - 将一个函数作为另一个方法的参数进行传递
class HighClassFunc01{
  def cook(food:String,howToCook:(String) => String)={
    howToCook(food);
  }
  //def htc(food:String):String = {}
  //小复习: 返回值类型可以推断,省略返回值类型声明; 在不引起歧义的情况下,省略return,直接返回函数最后一个表达式结果
  //由于方法体只有一行,其实大括号也是可以省略的! 注意: 函数体前的 = 只有在返回值类型为Unit时,即 :Unit= 可以省略
  def htc(food:String) = {
    "cooked " + food;
  }
  
  val r1 = cook("beef",htc);
  println(r1);
  
  //也可以不声明函数,直接在参数中写,有点像匿名内部类的使用了
  val r2 = cook("涮羊肉",(food:String)=>{food + "要加生姜去骚味!"})
  println(r2);
  
  val r3 = cook("孜然牛肉",(food:String)=>food + "味道一级棒");//函数体只有一句,可以省略函数体的大括号
  println(r3);
}

//高阶函数 - 将一个作为另一个方法的返回值进行返回
class HighClassFunc02{
  def lookUpMenu(cm:String):String => String = {
    if(cm == "beef"){
      //lookUpMenu函数将函数里头的函数作为返回值进行返回,funny!
      def cook(food:String) = {
        "cooked "+food;
      }
      //cook函数作为返回值返回!
      cook
    }else if(cm == "涮羊肉"){
      (food:String)=>{food+"要加生姜去骚"}
    }else if(cm == "孜然牛肉"){
    	(food:String)=>{food+"一级棒"}      
    }else{
      food => food+"太难做了";//在不引起歧义的情况下,可以将一个参数的参数列表括号也干掉
    }
  }
}

object testClass{
  def main(args: Array[String]): Unit = {
    val p = new Person02();
    print("Vince want to ");
    p.eat("beef");
    
    val s = new SumDemo();
    
    new HighClassFunc01();
    val cookFunc = new HighClassFunc02().lookUpMenu("涮羊肉");//先调用函数,获取另一个方法作为返回值
    val r1 = cookFunc("咸鱼")//通过得到的返回值函数,传值执行!
    println(r1)
  }  
  
  
  /* 小结:
   * 比较Java和Scala
   * 在java语言中,函数只能作为成员方法存在,不能作为局部属性,或者方法参数,或者方法返回值,功能不完整,是二等公民
   * 而在scala中,函数可以作为成员方法,也可以作为局部属性,可以用作方法参数和方法返回值,具有完整的功能,函数是一等公民
   * 
   * 在Java中有多重参数类型,而scala只有val常量,var变量!
   */
}