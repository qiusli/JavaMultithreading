package demo9;


import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by liqiushi on 7/5/15.
 */
// The Java BlockingQueue interface in the java.util.concurrent package represents a queue which is thread safe to put into,
// and take instances from.
public class App {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static void producer() throws InterruptedException {
        Random random = new Random();
        while (true) {
            // if the queue is not full, both methods succeed;
            // if the queue is full, add() fails with an exception whereas put() blocks.
            queue.put(random.nextInt(100));
        }
    }

    public static void consumer() throws InterruptedException {
        Random random = new Random();
        while (true) {
            Thread.sleep(100);
            if (random.nextInt(10) == 0) {
                Integer value = queue.take();
                System.out.println("value taken: " + value + "; queue size is: " + queue.size());
            }
        }
    }
}
