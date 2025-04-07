package Models;

import java.util.concurrent.Semaphore;

public class GeradorId {
    private static int nextId = 1;
    private static final Semaphore mutex = new Semaphore(1, true);

    public static int getNextId() {
        try {
            mutex.acquire();
            return nextId++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        } finally {
            mutex.release();
        }
    }
}
