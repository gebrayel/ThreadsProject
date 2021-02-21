
package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;

public class JoystickProducer extends Thread{
    
    private int dayDuration;
    private double dailyProduction = 0.5;
    
    public JoystickProducer (Semaphore mutex, Semaphore semBotones, int dayDuration, Semaphore semEnsamblador){
        this.dayDuration = dayDuration;
    }
    
    public void run(){
        while(true){
            try{
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduction));
                Main.Joysticks++;
                System.out.println("Joystick" + Main.Joysticks);
            }catch(Exception e){
                System.out.println("Joystick pelo bola");
            }
        }
    }
}
