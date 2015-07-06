package demo6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liqiushi on 7/4/15.
 */
public class Worker {
    private Object object1 = new Object();
    private Object object2 = new Object();

    private Random random = new Random();
    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();

    // each class has only one transient lock, if you have multiple synchronized method, they together
    // share the same lock and even if different threads is trying to run different synchronized method,
    // they still have to wait for the only lock
    public void stageOne() {
        synchronized (object1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    // The only real difference is that a synchronized block can choose which object it synchronizes on.
    // A synchronized method can only use 'this' (or the corresponding Class instance for a synchronized class method).
    public void stageTwo() {
        // Blocks do have advantages over methods, most of all in flexibility because you can use
        // other object as lock whereas syncing the method would lock the complete class.
        // Also if the method grows you can still keep the synchronized section separated
        synchronized (object2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }

    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void main() {
        System.out.println("starting...");
        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("time taken: " + (end - start));
        System.out.println("List1: " + list1.size() + "; List2: " + list2.size());
    }
}
