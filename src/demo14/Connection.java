package demo14;

import java.util.concurrent.Semaphore;

// The java.util.concurrent.Semaphore class is a counting semaphore. That means that it has two main methods:
//
//        acquire()
//        release()
//        The counting semaphore is initialized with a given number of "permits". For each call to acquire() a permit is taken by the calling thread. For each call to release() a permit is returned to the semaphore. Thus, at most N threads can pass the acquire() method without any release() calls, where N is the number of permits the semaphore was initialized with. The permits are just a simple counter. Nothing fancy here.
//
//        Semaphore Usage
//
//        As semaphore typically has two uses:
//
//        To guard a critical section against entry by more than N threads at a time.
//        To send signals between two threads.

// If you use a semaphore to send signals between threads, then you would typically have one thread call the acquire() method, and
// the other thread to call the release() method.

// If no permits are available, the acquire() call will block until a permit is released by another thread. Similarly,
// a release() calls is blocked if no more permits can be released into this semaphore.
public class Connection {

    private static Connection instance = new Connection();

    //    Mutex: exclusive-member access to a resource
    //    Semaphore: n-member access to a resourc
    private Semaphore sem = new Semaphore(10, true);

    private int connections = 0;

    private Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            doConnect();
        } finally {

            sem.release();
        }
    }

    public void doConnect() {
        synchronized (this) {
            connections++;
            System.out.println("Current connections: " + connections);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
        }
    }
}