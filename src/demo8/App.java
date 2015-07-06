package demo8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liqiushi on 7/5/15.
 */

// CountDownLatch works in latch principle, main thread will wait until gate is open. One thread waits for n number of threads
// specified while creating CountDownLatch in Java. Any thread, usually main thread of application, which calls
// CountDownLatch.await() will wait until count reaches zero or its interrupted by another thread. All other thread are required
// to do count down by calling CountDownLatch.countDown() once they are completed or ready.
// As soon as count reaches zero, Thread awaiting starts running. One of the disadvantages/advantages of CountDownLatch is that
// its not reusable once count reaches to zero you can not use CountDownLatch any more.

// edit:Use CountDownLatch when one thread like main thread, requires to wait for one or more thread to complete, before it can start processing.
// Classical example of using CountDownLatch in Java is any server side core Java application which uses services architecture, where multiple services are provided by multiple threads and application can not start processing until all services have started successfully.
class Processor implements Runnable {
    CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("staring...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}

public class App {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i = 0; i < 3; i++) {
            executorService.submit(new Processor(latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("completed");
    }
}
