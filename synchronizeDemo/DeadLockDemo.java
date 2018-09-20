package com.demo.synchronize;

public class DeadLockDemo {
	static Printer p = new Printer();
	static Scanner s = new Scanner();
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (p) {
					p.print();
					try {
						Thread.sleep(10);//线程挂起10毫秒,但是不释放锁的资源
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (s) {
						s.scan();//这边要,但是又被另一个抢了
					}
				}
			}
		}).start();
		//Runnable虽然是接口,但是可以通过匿名内部类实现一个多线程
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (s) {
					s.scan();//抢了Scanner,上面的那个线程,凉了,这线程要Printer,那边不放
					synchronized (p) {
						p.print();
					}
				}
			}
		}).start();
		
	}
	/*
	 * 有限的共享资源,多个线程,而锁又相互嵌套!
	 * 锁导致多个线程相互等待致使程序无法继续往下执行
	 * 避免死锁的方法:1.减少线程数量,2.减少共享资源,3.统一锁对象,4.避免锁嵌套
	 */

}

class Scanner{
	public void scan() {
		System.out.println("扫描仪在吭哧吭哧的扫描~~~");
	}
}

class Printer {
	public void print() {
		System.out.println("打印机在噗呲噗呲的打印~~~");
	}
}