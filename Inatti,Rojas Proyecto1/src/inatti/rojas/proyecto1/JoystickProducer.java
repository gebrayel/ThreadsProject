
package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;

public class JoystickProducer extends Thread{
    
    private int dayDuration;
    private double dailyProduction = 0.5;
    private Semaphore mutex;
    private Semaphore semJoystick;
    private Semaphore semEnsamblador;
    private boolean stop;
    
    public JoystickProducer (Semaphore mutex, Semaphore semJoystick, int dayDuration, Semaphore semEnsamblador){
        this.dayDuration = dayDuration;
        this.semJoystick = semJoystick;
        this.mutex = mutex;
        this.semEnsamblador = semEnsamblador;
    }
    
    public void run(){
        while(true){
            if (!this.stop){
                try{
                semJoystick.acquire();
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduction));
                mutex.acquire();
                Main.Joysticks++;
                System.out.println("Joystick" + Main.Joysticks);
                mutex.release();
                semEnsamblador.release();
                }catch(Exception e){
                    System.out.println("Joystick pelo bola");
                }
            }
            
        }

    }
}
