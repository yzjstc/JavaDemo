package com.demo.synchronize;

public class ProducerConsumerDemo {

    public static void main(String[] args) {
        Product p = new Product();
        new Thread(new Producer(p)).start();
        new Thread(new Producer(p)).start();
        new Thread(new Consumer(p)).start();
        new Thread(new Consumer(p)).start();
    }

}

class Product {
    private int count;
    boolean flag = true;//设置标志位,避免连续多次同一类型的线程执行

    public Product() {
    }

    public Product(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}

class Producer implements Runnable {
    private Product p;
    
    public Producer(Product p) {
        this.p = p;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (p) {
                while(p.flag == false) {
                    try {
                        p.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //生产产品,但总数量保证不超过1000
                int count = (int) ((1001 - p.getCount()) * Math.random());
                p.setCount(p.getCount() + count);
                System.out.println("本次生产了:"+count+"件产品,共有:"+p.getCount()+"件产品!!!");
                
                p.flag = false;//置为false, 生产者不能再生产,而消费者可以
                p.notifyAll();
            }
            
        }
    }

}

class Consumer implements Runnable {
    private Product p;

    public Consumer(Product p) {
        this.p = p;
    }

    @Override
    public void run() {
        while(true) {
            //消费产品, 不超过现有的产品数
            synchronized (p) {
                while(p.flag == true) {//用while避免轮回唤醒
                	try {
						p.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
                int count = (int)(p.getCount() * Math.random() + 1);
                p.setCount(p.getCount() - count);
                System.out.println("本次消费了:"+count+"件产品,还有:"+p.getCount()+"件产品");
                
                p.flag = true;
                p.notifyAll();
            }
            
        }
    }

}