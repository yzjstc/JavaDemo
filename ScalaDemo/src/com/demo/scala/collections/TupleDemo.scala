package com.demo.scala.collections

import java.util.Date
import scala.io.Source

object TupleDemo {
  def main(args: Array[String]): Unit = {
    /**
     * Tuple元祖
     * 所谓的tuple就是 一组值的集合,可以表示一条由多个值组成的数据结构
     */
    //创建元祖
    val t1 = (1,"zs","bj",19,123.56,new Date());
    val t2 = 3->"xxx"
    
    //访问元祖
    println(t1._2)
    
    /*
     * attention
     * 元组的内存放的元素的个数是有限制的,最多22个
     * 含有不同数量的元组的 类型是不同的 ,一个元素的元组为Tuple1类型,两个 元素的元组为Tuple2类型以此类推,最多到Tuple22
     */
    
    /**
     * 集合类的通用方法
     */
    //1.exists 判断集合中是否存在符合指定条件的数据
    val l1 = List(1,2,3,4,5,6,7,8,9);
    val r1 = l1.exists(_>10);
    println(r1)
    
    //2.Sorted sortWith 排序
    val l2 = List(1,3,2,34,6,23,657,23,11);
    val l3 = l2.sorted;
    l3.foreach(println(_));
    val l4 = l2.sortWith((n1:Int,n2:Int)=>{n1>n2});
    l4.foreach(println(_));
    
    //3.Distinct去重
    val l5 = List(1,3,5,2,4,6,4,2,1);
    val l6 = l5.distinct;
    l6.foreach(println(_));
    
    //4.Reverse、 ReverseMap反转
    val l7 = List(1,2,3,4,5);
    val l8 = l7.reverse;
    l8.foreach(println(_));
    
    val l9 = l7.reverseMap(_*10);
    l9.foreach(println(_));
    
    //5.Contains、 startWith、 endWith 判断是按条件包含
    val l10 = List(1,3,5,7,9);
    val r01 = l10.contains(4);
    println(r01);
    val r02 = l10.startsWith(List(1,3));
    println(r02);
    val r03 = l10.endsWith(List(9,10));
    println(r03);
    
    //交集intersect 差集diff 并集union
    val l11 = List(1,3,5,7,9);
    val l12 = List(5,7,9,11,13);
    val l13 = l11.intersect(l12);
    l13.foreach(println(_));
    val l14 = l11.diff(l12);
    l14.foreach(println(_));
    val l15 = l11.union(l12);
    l15.foreach(println(_));
    
    //集合类中的高阶函数
    //1.partition拆分
    //将一个集合按一个布尔值分成两个集合，满足条件的一个集合，其他另外一个集合
    val lp1 = List(1,2,3,4,5,6,7,8,9);
    val lp2 = lp1.partition(_%2==0);
    println(lp2);
    
    //map映射
    //通过给定函数映射，把一个集合转换为另外一个集合。集合中元素个数不变
    val lm1 = List(1,2,3,4,5);
    val lm2 = lm1.map { _*100 }
    println(lm2)
    
    //filter、 filterNot过滤
    //用来过滤数据，要求函数返回布尔值，true留下，false丢弃
    val lf1 = List(1,3,56,23,65567,234,23);
    val lf2 = lf1.filter { _<100 }
    val lf3 = lf1.filterNot { _<100 }
    println(lf2)
    println(lf3)
    
    //reduce化简
    //重复运算，将上次的运行结果和下一个值进行运算
    val lr1 = List(1,2,3,4,5);
    val rr1 = lr1.reduce(_*10+_);//首次计算时,运行结果默认为0
    println(rr1)//输出12345
    
    //par多线程并发
    //在scala中如果有大量重复的操作，且这些操作满足交换律和结合律，则可以通过par操作自动开启多线程，并发的执行处理
    val lpar1 = (1 to 1000000)
    val begin = System.currentTimeMillis()
    val rpar1 = lpar1.par.reduce((x:Int,y:Int) => { println(Thread.currentThread().getName); x+y;});
    println(rpar1)
    val end = System.currentTimeMillis()
    println(end - begin)
    
    //groupBy分组
    //分组操作可以将集合中的数据按照指定方式分组
    val lg1 = List(1,2,34,6345,324,12,54,2345,3);
    val rg1 = lg1.groupBy { _<100 };
    println(rg1);
    val lg2 = List(("bj"->"zs"),("sh"->"lisi"),("gz"->"ww"),("bj"->"zl"),("gz"->"qq"));
    val rg2 = lg2.groupBy(_._1);
    println(rg2);
    
    //mapValues值映射
    //对map集合中的值做映射
    val m1 = Map("zs"->19,"ls"->20,"ww"->18);
    val m2 = m1.mapValues(_+1)
    println(m2)
    
    
    /*
     * 利用scala集合类的高阶函数实现wordCount
     */
       Source.fromFile("words.txt").getLines()
       .toList.map((_,1))
       .groupBy(_._1)
       .mapValues(_.map(_._2))
       .mapValues (_.reduce(_+_))
       .foreach(println(_));
  }
  
}