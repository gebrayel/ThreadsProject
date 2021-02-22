package Classes;

import Windows.*;
import java.util.concurrent.Semaphore;

public class JoystickProducer extends Thread {
    
    private int dayDuration;
    private double dailyProduction = 0.5;
    private Semaphore mutex;
    private Semaphore semJoystick;
    private Semaphore semEnsamblador;
    private boolean stop;
    
    public JoystickProducer(int dayDuration, Semaphore mutex, Semaphore semJoystick, Semaphore semEnsamblador) {
        this.dayDuration = dayDuration;
        this.semJoystick = semJoystick;
        this.mutex = mutex;
        this.semEnsamblador = semEnsamblador;
    }
    
    public void run() {
        while (true) {
            if (!this.stop) {
                try {
                    semJoystick.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduction));
                    mutex.acquire();
                    Menu.Joysticks++;
                    Menu.JoystickStorage.setText(Integer.toString(Menu.Joysticks));
                    Menu.OutputConsole.setText(Menu.OutputConsole.getText() + "Joysticks -> " + Menu.Joysticks + "\n");
                    mutex.release();
                    semEnsamblador.release();
                } catch (Exception e) {
                    System.out.println("Joystick pelo bola");
                }
            }
            
        }
        
    }
}
