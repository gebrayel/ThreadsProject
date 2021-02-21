package inatti.rojas.proyecto1;

import java.util.concurrent.Semaphore;

public class ButtonProducer extends Thread {

    private int dayDuration;
    private double dailyProduce = 2;
    private Semaphore mutex;
    private Semaphore semBotones;
    private Semaphore semEnsamblador;
    private boolean stop;
    
    public ButtonProducer(Semaphore mutex, Semaphore semBotones, int dayDuration, Semaphore semEnsamblador/*, Semaphore semaphore*/) {

        this.dayDuration = dayDuration;
        this.mutex = mutex;
        this.semBotones = semBotones;
        this.semEnsamblador = semEnsamblador;
        this.stop = false;
        
    }

    public void run() {
        while (true) {
            if (!this.stop) {
                try {
                    semBotones.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                    mutex.acquire();
                    Main.Buttons++;
                    System.out.println("Boton" + Main.Buttons);
                    mutex.release();
                    semEnsamblador.release();
                    
                } catch (Exception e) {

                }
            }
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
}
