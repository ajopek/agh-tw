import java.util.Random;

class Philosophers {
    public static void main(String[] args) {
        algorithm3();
    }

    private static void algorithm1() {
        Waiter waiter = new Waiter();
        Philosopher1[] philosophers = new Philosopher1[5];
        for (int i = 0; i < 5; i++) philosophers[i] = new Philosopher1(i, waiter);

        for (int i = 0; i < 5; i++) philosophers[i].start();
        try {
            for (int i = 0; i < 5; i++) philosophers[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void algorithm3() {
        BinarySemaphore[] semaphores = new BinarySemaphore[5];
        for(int i=0; i < 5; i++) semaphores[i] = new BinarySemaphore();

        Philosopher3[] philosophers = new Philosopher3[5];
        for(int i=0; i < 5; i++) philosophers[i] = new Philosopher3(semaphores, i);

        for(int i=0; i < 5; i++) philosophers[i].start();
        try {for(int i=0; i < 5; i++) philosophers[i].join();}
        catch (InterruptedException e) {e.printStackTrace();}
    }

    private static void algorithm2() {
        BinarySemaphore[] philosophersSemaphore = new BinarySemaphore[5];
        for(int i=0; i < 5; i++) philosophersSemaphore[i] = new BinarySemaphore();

        Philosopher2[] philosophers = new Philosopher2[5];
        for(int i=0; i < 5; i++) philosophers[i] = new Philosopher2(philosophersSemaphore, i);

        for(int i=0; i < 5; i++) philosophers[i].start();
        try {for(int i=0; i < 5; i++) philosophers[i].join();}
        catch (InterruptedException e) {e.printStackTrace();}
    }
}

// rozwiązane z użyciem zarządcy zasobów który zarządza kto je
class Philosopher1 extends Thread {
    private int n;
    private Waiter waiter;

    Philosopher1(int n, Waiter waiter) {
        this.n = n;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                waiter.requestChopsticks(n);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(n + " is eating");
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(3333) + 1203);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(n + " ended eating");
            waiter.collect(n);

            System.out.println(n + " is thinking...");
            try {
                Thread.sleep(rand.nextInt(3333) + 1203);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class Waiter {
    private boolean[] conds = new boolean[5];

    Waiter() {
        for (int i = 0; i < 5; i++) conds[i] = true;
    }

    synchronized void collect(int i) {
        conds[i] = conds[(i + 1) % 5] = true;
        this.notify();
    }

    synchronized void requestChopsticks(int i) throws InterruptedException {
        while (!conds[i] || !conds[(i + 1) % 5]) this.wait();
        conds[i] = conds[(i + 1) % 5] = false;
    }
}

// Filozofowie próbują po kolei. Możliwe zakleszczenia
class Philosopher2 extends Thread {
    static BinarySemaphore[] semaphores;
    int n;

     Philosopher2(BinarySemaphore[] semaphores, int n) {
        this.semaphores = semaphores;
        this.n = n;
    }

    synchronized void take() throws InterruptedException {
        semaphores[((this.n - 1) % 5 + 5) % 5].acquire();
        semaphores[this.n].acquire();
    }

    synchronized void put() {
        semaphores[((this.n - 1) % 5 + 5) % 5].release();
        semaphores[this.n].release();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(n + " is eating");
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(3333) + 1323);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(n + " ended");
            put();
            try {
                Thread.sleep(rand.nextInt(3333) + 1323);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// rozwiązanie z użyciem hierarchii zasobów
 class Philosopher3 extends Thread {
    private final BinarySemaphore[] binarySemaphores;
    private int id;


     Philosopher3(BinarySemaphore[] semaphores, int id) {
        this.binarySemaphores = semaphores;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();


            binarySemaphores[this.id == 2 ? (this.id + 1) % 5 : this.id].acquire();
            
            binarySemaphores[this.id == 2 ? this.id : (this.id + 1) % 5].acquire();

            System.out.println(id + " is eating");
            try {
                Thread.sleep(rand.nextInt(3333) + 1323);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            binarySemaphores[(this.id + 1) % 5].release();
            binarySemaphores[this.id].release();
            System.out.println(id + " finished eating");


            System.out.println(id + " is thinking...");
            try {
                Thread.sleep(rand.nextInt(3333) + 1323);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
