package demo11;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by liqiushi on 7/5/15.
 */
public class Processor {
    private List<Integer> list = new LinkedList<Integer>();
    final int limit = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {
                while (list.size() == limit) {
                    // call wait on the lock object
                    lock.wait();
                }
                list.add(value++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait();
                }
                System.out.print("list size is: " + list.size());
                int value = list.remove(0);
                System.out.println("; value is: " + value);
                lock.notify();
            }
            Thread.sleep(random.nextInt(1000));
        }
    }
}
