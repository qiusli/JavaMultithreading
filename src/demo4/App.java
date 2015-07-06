package demo4;

import java.util.Scanner;

/**
 * Created by liqiushi on 7/4/15.
 */
class Processor extends Thread {
    // current thread may cache this value
    // volatile is used to indicate that a variable's value will be modified by different threads
    private volatile boolean running = true;
    public void run() {
        while(running) {
            System.out.println("hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown() {
        running = false;
    }
}
public class App {
    public static void main(String[] args) {
        Processor proc1 = new Processor();
        proc1.start();

        System.out.println("hit enter to stop...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        proc1.shutDown();
    }
}
