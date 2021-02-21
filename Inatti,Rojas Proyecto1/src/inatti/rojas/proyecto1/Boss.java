package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;
public class Boss extends Thread {
    
    private int dayDuration;
    private double sleepTime=24/18f;
    private double workingTime=24/6f;
    private Semaphore semDaysforDeploy;
    public Boss(int dayDuration, Semaphore semDaysforDeploy){
        
        this.dayDuration = dayDuration;
        this.semDaysforDeploy = semDaysforDeploy;
        
    }
    
    public void run(){
        while(true){
            
            try{
                System.out.println("el boss a mimir");
                Thread.sleep(Math.round((dayDuration*1000)/sleepTime));
                System.out.println("el boss a chambear");
                semDaysforDeploy.acquire();
                Main.DaysforDeploy--;
                Thread.sleep(Math.round((dayDuration*1000)/workingTime));
                semDaysforDeploy.release();
                
            }catch(Exception e){
                System.out.println("pelo bola el boss");
            }
        }
    }
    
}
