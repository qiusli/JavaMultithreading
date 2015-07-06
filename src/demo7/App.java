package demo7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liqiushi on 7/5/15.
 */
class Processor implements Runnable {
    private int id;

    public Processor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("starting id: " + id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ending id: " + id);
    }
}

public class App {
    public static void main(String[] args) {
        // like 2 workers in the factory, start working on another task once finishing one task

        // The java.util.concurrent.ExecutorService interface represents an asynchronous execution mechanism which is
        // capable of executing tasks in the background. An ExecutorService is thus very similar to a thread pool.
        // In fact, the implementation of ExecutorService present in the java.util.concurrent package is a thread pool implementation.
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executor.submit(new Processor(i));
        }

        // When you are done using the ExecutorService you should shut it down, so the threads do not keep running.

        // For instance, if your application is started via a main() method and your main thread exits your
        // application, the application will keep running if you have an active ExexutorService in your application.
        // The active threads inside this ExecutorService prevents the JVM from shutting down.

        // To terminate the threads inside the ExecutorService you call its shutdown() method. The
        // ExecutorService will not shut down immediately, but it will no longer accept new tasks, and once all threads
        // have finished current tasks, the ExecutorService shuts down. All tasks submitted to the ExecutorService
        // before shutdown() is called, are executed.
        executor.shutdown();

        System.out.println("All tasks submitted");

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks completed");
    }
}
