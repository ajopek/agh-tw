class Counter {
    private int counter = 0;
    private final BinarySemaphore sem = new BinarySemaphore();

    void incrementCounter() {
        sem.acquire();
        counter++;
        sem.release();
    }

    void decrementCounter() {
        sem.acquire();
        counter--;
        sem.release();
    }

    public int getValue() {
        return this.counter;
    }
}

class Racer extends Thread {
    private final int amount;
    private final boolean isInc;
    private Counter counter;

    Racer(int amount, boolean inc, Counter counter) {
        this.amount = amount;
        isInc = inc;
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < amount; i++) {
            if(this.isInc) {
                counter.incrementCounter();
            } else {
                counter.decrementCounter();
            }
        }
    }
}


class BinarySemaphore {
    int access = 1;
    Object monitor = new Object();

    void acquire() {
        try{
            synchronized (monitor) {
                while(access == 0) {
                    monitor.wait();
                }
                access = 0;
            }
        } catch (InterruptedException ex) {
            System.out.println("Interrupted: " + ex.getMessage());
            System.exit(1);
        }
    }

    void release() {
        synchronized (monitor) {
            if (access == 0) {
                access = 1;
                monitor.notify();
            }
        }
    }
}

class CountingSemaphore {

    private int n;
    private final BinarySemaphore binarySemaphore = new BinarySemaphore();

    CountingSemaphore(int n) {
        this.n = n;
    }

    void acquire() throws InterruptedException {
        if (n > 0) {
            if ((--n) == 0) {
                binarySemaphore.acquire();
            }
        }
    }

    void release() throws InterruptedException {
        if((n++)==0){
            binarySemaphore.release();
        }
    }
}

public class TW2 {
    public static void main(String[] args) {
        Counter race = new Counter();
        Racer racer1 = new Racer(101, true, race);
        Racer racer2 = new Racer(100, false, race);
        racer1.start();
        racer2.start();


        try {
            racer1.join();
            racer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println(race.getValue());
    }
}
