package justfortest;

public class TestStaticBlock {
	static int x,y;//默认初始化,int的默认值是0
	static{
		int x = 5;//在第一次被加载JVM时运行,但是由于是局部变量,x=5不影响后面的值
		//x = 5; //如果前面没有int,则用的是类的静态变量,对后面的运算有影响
	}
	public static void main(String[] args) {
		x--;
		myMethod();//static块中int x=5,在执行该方法后,x是1,y是0
		System.out.println(x + y++ + x);
		
		int j = 0;
		for (int i = 0; i < 100; i++){
			j=j++;
			/*
			 * java用了中间缓存变量的机制
			 * j=j++
			 * 实际如下:
			 * temp = j;
			 * j = j + 1;
			 * j = temp;
			 */
		}
		System.out.println(j);//输出0
		
	}
	public static void myMethod(){
		y = x++ + ++x;
		/*
		 * x++ 先运算,得到是x自增前的值
		 * ++x 再运行,得到x自增后的值
		 * + 和运算最后执行
		 */
	}

}
