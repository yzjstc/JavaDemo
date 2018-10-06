package com.demo.scala

object PackageDemo {
  /**
   * scala也有包的概念,和java相同,可以用来组织代码结构,实现分包存放代码
   * 1.通过package关键字声明包
   * package com.demo.scala.xxx
   * scala的包的声明决定了编译后的.class文件所处的包结构,而声明包和.scala文件所处的包即使不同也没有关系
   */
}
//通过package代码块在同一个scala文件中声明多个包层级结构
package com{
  package test{
    class Person{}
    package demo{
      class Demo{}
    }
  }
}

/*
 * scala包的使用比java灵活很多,但是仅作了解,不建议使用该灵活的包结构,这样会造成后续阅读代码和调试代码过程的不必要的麻烦
 */


