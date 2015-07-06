package demo12;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liqiushi on 7/5/15.
 */
public class Runner {
    private int count = 0;
    // do the same work as synchronized
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();

        System.out.println("waiting ... ");
        condition.await();

        System.out.println("woken up");

        try {
            increment();
        }
        // unlock will never get called if increment get an exception, so increment should be in try-catch block
        finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(500);

        lock.lock();

        System.out.println("press the return key ...");
        new Scanner(System.in).nextLine();
        System.out.println("got return key ...");

        condition.signal();
        System.out.println("after signal");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finish() {
        System.out.println("count is: " + count);
    }
}
