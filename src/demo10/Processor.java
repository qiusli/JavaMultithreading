package demo10;

import java.util.Scanner;

/**
 * Created by liqiushi on 7/5/15.
 */
public class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("producer start running ...");
            // wait() tells the calling thread to give up the monitor and go to sleep until some other thread
            // enters the same monitor and calls notify( ).

            // One key difference is that while sleeping a Thread does not release the locks it
            // holds, while waiting releases the lock on the object that wait() is called on.
            wait();
            System.out.println("producer resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        Scanner scanner = new Scanner(System.in);
        synchronized (this) {
            System.out.println("waiting for return key");
            scanner.nextLine();
            System.out.println("return key pressed");
            // can only be called within a synchronized block
            // does not relinquish the lock but will still finish executing all the stuffs within this synchronized
            // block
            notify();
        }
    }
}
