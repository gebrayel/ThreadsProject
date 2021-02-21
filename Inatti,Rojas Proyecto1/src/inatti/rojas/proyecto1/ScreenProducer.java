package inatti.rojas.proyecto1;

import java.util.concurrent.Semaphore;

public class ScreenProducer extends Thread {

    private int dayDuration;
    private double dailyProduce = 1;
    private boolean stop;
    private Semaphore mutexNormalScreens;
    private Semaphore semNormalScreens;
    private Semaphore semEnsambladorNormalScreens;
    private Semaphore mutexTouchScreens;
    private Semaphore semTouchScreens;
    private Semaphore semEnsambladorTouchScreens;

    public ScreenProducer(int dayDuration, Semaphore mutexNormalScreens, Semaphore semNormalScreens, Semaphore semEnsambladorNormalScreens, Semaphore mutexTouchScreens, Semaphore semTouchScreens, Semaphore semEnsambladorTouchScreens) {
        this.dayDuration = dayDuration;
        this.stop = false;
        this.mutexNormalScreens = mutexNormalScreens;
        this.semNormalScreens = semNormalScreens;
        this.semEnsambladorNormalScreens = semEnsambladorNormalScreens;
        this.mutexTouchScreens = mutexTouchScreens;
        this.semTouchScreens = semTouchScreens;
        this.semEnsambladorTouchScreens = semEnsambladorTouchScreens;
    }

    public void run() {
        while (true) {
            if (!stop) {
                try {
                    semNormalScreens.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                        mutexNormalScreens.acquire();
                        Main.NormalScreens++;
                        System.out.println("Normal" + Main.NormalScreens);
                        mutexNormalScreens.release();
                    semEnsambladorNormalScreens.release();
                    
                    this.dailyProduce = 0.5;
                    
                    semTouchScreens.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                    mutexTouchScreens.acquire();
                    Main.TouchScreens++;
                    System.out.println("Tactil" + Main.TouchScreens);
                    mutexTouchScreens.release();
                    semEnsambladorTouchScreens.release();
                    
                    this.dailyProduce = 1;
                } catch (Exception e) {

                }
            }
        }
    }

}
