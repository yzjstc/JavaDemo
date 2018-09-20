package com.demo.synchronize;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDemo {

	public static void main(String[] args) {
		/*
		 * 继承Thread类和实现Runnable接口的类,开启多线程,通过Thread的构造方法声明
		 * 然后调用start(),就可以执行多线程,执行里头的run方法
		 * 如果直接调用run方法,就和实现普通方法一样
		 */
		Thread t1 = new Thread(new TDemo());
		Thread t2 = new Thread(new RDemo());
		
		//声明一个线程池
		ExecutorService esPoll = Executors.newCachedThreadPool();
		
		t1.start();
		t2.start();
		/*
		 * 而实现Callable接口的类,如果要开启多线程, 需要通过线程池submit才能执行
		 * 返回值被封装到Future中,通过get方法获取线程执行call方法后的返回值
		 */
		Future<String> f = esPoll.submit(new CDemo());
		try {
			System.out.println(f.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//线程池shutdown得等到提交的线程执行完,才能关闭线程池
		//线程池调用shutdownNow方法,立即关闭线程池,把没有完成线程装到一个list中返回来
		esPoll.shutdown();
		
		/*
		 * 并发最大的问题在于,线程不安全呐,如果多执行几次,就能发现,
		 * 执行顺序不见得是三个不同线程的调用顺序
		 * 而调用start()和submit(),就是开启一个新的线程,线程在JVM中争夺资源,谁抢到谁执行
		 */
	}

}

/*
 * 开启多线程方式一:
 * 继承java.lang.Thread类,并重写其中的run方法
 */
class TDemo extends Thread{
	@Override
	public void run() {
		for(int i = 0; i < 100; i++){			
			System.out.println("TDemo"+i);
		}
	}
}

/*
 * 开启多线程的方式二:
 * 实现java.lang.Runable接口,并重写其中的run方法
 */
class RDemo implements Runnable{

	@Override
	public void run() {
		for(int i = 0; i < 100; i++){
			System.out.println("RDemo"+i);
		}
	}
	
}

/*
 * 开启多线程的方式三:
 * 实现java.util.concurrent.Callable接口,并重写call方法
 * concurrent包里面很多实现了线程安全的锁/阻塞队列/concurrentHashMap,非常重要啊
 * 这个实现多线程的方式,比起继承Thread类和实现Runable接口,特别之处在于:有返回值!
 */
class CDemo implements Callable<String>{

	@Override
	public String call() throws Exception {
		for(int i = 0; i < 100; i++){
			System.out.println("CDemo"+i);
		}
		return "it is done";
	}
	
}
