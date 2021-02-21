
package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;
/**
 *
 * @author diosito
 */
public class SDProducer extends Thread{
    private int dayDuration;
    private double dailyProduction = 1/3f;
    private Semaphore mutex;
    private Semaphore semSD;
    private Semaphore semEnsamblador;
    private boolean stop;
    
    public SDProducer (Semaphore mutex, Semaphore semSD, int dayDuration, Semaphore semEnsamblador){
        this.dayDuration = dayDuration;
        this.semSD = semSD;
        this.mutex = mutex;
        this.semEnsamblador = semEnsamblador;
    }
    
    public void run(){
        while(true){
            if (!this.stop){
                try{
                semSD.acquire();
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduction));
                mutex.acquire();
                Main.SD++;
                System.out.println("SD: " + Main.SD);
                mutex.release();
                semEnsamblador.release();
                }catch(Exception e){
                    System.out.println("SD pelo bola");
                }
            }
            
        }
    }
}
