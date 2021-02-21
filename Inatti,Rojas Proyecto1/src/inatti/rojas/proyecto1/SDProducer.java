
package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;
/**
 *
 * @author diosito
 */
public class SDProducer extends Thread{
    private int dayDuration;
    private double dailyProduction = 1/3f;
    
    public SDProducer (Semaphore mutex, Semaphore semBotones, int dayDuration, Semaphore semEnsamblador){
        this.dayDuration = dayDuration;
    }
    
    public void run(){
        while(true){
            try{
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduction));
                Main.SD++;
                System.out.println("SD" + Main.SD);
            }catch(Exception e){
                System.out.println("SD PELO BOLA");
            }
        }
    }
}
