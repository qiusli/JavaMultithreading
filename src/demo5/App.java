package demo5;

/**
 * Created by liqiushi on 7/4/15.
 */
public class App {
    private int count = 0;

    public static void main(String[] args) {
        App app = new App();
        app.doWork();
    }

    private void doWork() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        // The current thread invokes this method on a second thread, causing the current thread to block
        // until the second thread terminates or the specified number of milliseconds passes.
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count is : " + count);
    }

    // The synchronized keyword is all about different threads reading and writing to the same variables,
    // objects and resources. This is not a trivial topic in Java, but here is a quote from Sun:

    // Synchronized methods enable a simple strategy for preventing thread interference and memory consistency errors:
    // if an object is visible to more than one thread, all reads or writes to that object's variables are done
    // through synchronized methods.
    // In a very, very small nutshell: When you have two threads that are reading and writing to the same 'resource',
    // say a variable named foo, you need to ensure that these threads access the variable in an atomic way. Without
    // the synchronized keyword, your thread 1 may not see the change thread 2 made to foo, or worse, it may only be
    // half changed. This would not be what you logically expect.
    private synchronized void increment() {
        count++;
    }
}
