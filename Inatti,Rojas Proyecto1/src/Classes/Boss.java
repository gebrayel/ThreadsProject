<<<<<<< HEAD:Inatti,Rojas Proyecto1/src/Classes/Boss.java
package inatti.rojas.Classes;

=======
package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;
>>>>>>> 556dd58fd47c37be6695368cbb0005b89148e785:Inatti,Rojas Proyecto1/src/inatti/rojas/proyecto1/Boss.java
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
