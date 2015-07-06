package demo1;

/**
 * Created by liqiushi on 7/4/15.
 */
class Runner extends Thread {
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println("hello " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        // 如果直接调用run会在main线程里面,调用start会另开一个线程
        Runner runner1 = new Runner();
        runner1.start();

        Runner runner2 = new Runner();
        runner2.start();
    }
}
