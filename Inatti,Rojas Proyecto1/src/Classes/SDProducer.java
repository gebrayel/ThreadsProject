package Classes;

import Windows.*;
import java.util.concurrent.Semaphore;

public class SDProducer extends Thread {

    private int dayDuration;
    private double dailyProduction = 1 / 3f;
    private Semaphore mutex;
    private Semaphore semSD;
    private Semaphore semEnsamblador;
    private boolean stop;

    public SDProducer(int dayDuration, Semaphore mutex, Semaphore semSD, Semaphore semEnsamblador) {
        this.dayDuration = dayDuration;
        this.semSD = semSD;
        this.mutex = mutex;
        this.semEnsamblador = semEnsamblador;
    }

    public void run() {
        while (true) {
            if (!this.stop) {
                try {
                    semSD.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduction));
                    mutex.acquire();
                    Menu.SDCards++;
                    Menu.SDCardStorage.setText(Integer.toString(Menu.SDCards));
                    if (Menu.OutputSDCards.getText().split("\n").length != 10) {
                        Menu.OutputSDCards.setText(Menu.OutputSDCards.getText() + "SD Cards -> " + Menu.SDCards + "\n");
                    }else{
                        Menu.OutputSDCards.setText("SD Cards -> " + Menu.SDCards + "\n");
                    }
                    mutex.release();
                    semEnsamblador.release();
                } catch (Exception e) {
                    System.out.println("SD pelo bola");
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
