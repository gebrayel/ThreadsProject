package inatti.rojas.proyecto1;


public class Semaphore {
    
    public static volatile int x = 0;
    public static volatile int storage;
    public int y;
    
    public Semaphore(int storage){
        this.storage = storage;
    }

    public static int getX() {
        return x;
    }

    public static void setX(int x) {
        Semaphore.x = x;
    }

    public static int getStorage() {
        return storage;
    }

    public static void setStorage(int storage) {
        Semaphore.storage = storage;
    }
    
    
    
}
