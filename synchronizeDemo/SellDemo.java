package com.demo.synchronize;

public class SellDemo {

	public static void main(String[] args) {
		int ticketNum = 1000;
		Ticket t = new Ticket(ticketNum);
		//启动多个线程,并给每个线程起个名
		new Thread(new Seller(t),"一号售票员").start();
		new Thread(new Seller(t),"二号售票员").start();
		new Thread(new Seller(t),"三号售票员").start();
		new Thread(new Seller(t),"四号售票员").start();
	}

}

class Seller implements Runnable{
	//共享资源成为其属性
	private Ticket t;
	
	public Seller(Ticket t) {
		this.t = t;
	}
	@Override
	public void run() {
		while(true){
			//加锁实现多线程安全,synchronized块中的代码顺序执行,其他线程不能执行,
			//但是一离开synchronize块就多线程抢占资源
			synchronized (t) {//1.可对共享资源加锁
//			synchronized (Seller.class){//2.可对多线程类的字节码加锁
//			synchronized (this){//3.对this加锁
				if(t.getTicketNum() <= 0){
					break;
				}
				t.setTicketNum(t.getTicketNum()-1);
				System.out.println(Thread.currentThread().getName()+"卖了一张票,还剩"+t.getTicketNum()+"张");
			}
		}
	}
	
}

class Ticket{
	private int ticketNum;
	public Ticket(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	public int getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	
}
