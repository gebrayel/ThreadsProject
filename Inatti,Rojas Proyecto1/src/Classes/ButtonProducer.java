package Classes;

import Windows.*;
import java.util.concurrent.Semaphore;

public class ButtonProducer extends Thread {

    private int dayDuration;
    private boolean stop;
    private double dailyProduce = 2;
    private Semaphore mutex;
    private Semaphore semBotones;
    private Semaphore semEnsamblador;

    public ButtonProducer(int dayDuration, Semaphore mutex, Semaphore semBotones, Semaphore semEnsamblador) {

        this.dayDuration = dayDuration;
        this.stop = false;
        this.mutex = mutex;
        this.semBotones = semBotones;
        this.semEnsamblador = semEnsamblador;

    }

    public void run() {
        while (!this.stop) {
            try {
                semBotones.acquire();
                Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                mutex.acquire();
                Menu.Buttons++;
                Menu.ButtonStorage.setText(Integer.toString(Menu.Buttons));
                Menu.OutputConsole.setText(Menu.OutputConsole.getText() + "Botones -> " + Menu.Buttons + "\n");
                mutex.release();
                semEnsamblador.release();

            } catch (Exception e) {

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
