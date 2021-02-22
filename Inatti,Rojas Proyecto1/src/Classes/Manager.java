<<<<<<< HEAD:Inatti,Rojas Proyecto1/src/Classes/Manager.java
package Classes;

=======
package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;
>>>>>>> 556dd58fd47c37be6695368cbb0005b89148e785:Inatti,Rojas Proyecto1/src/inatti/rojas/proyecto1/Manager.java
public class Manager extends Thread {
    
    private int dayDuration;
    private int loteDuration;
    private double sleepTime=24/2f;
    private Semaphore semDaysforDeploy;
    public Manager(int dayDuration, int loteDuration, Semaphore semDaysforDeploy){
        
        this.dayDuration = dayDuration;
        this.semDaysforDeploy = semDaysforDeploy;
        this.loteDuration = loteDuration;
    }
    
    public void run(){
        while(true){
            
            try{
                System.out.println("el boss a mimir");
                Thread.sleep(Math.round((dayDuration*1000)/sleepTime));
                System.out.println("el boss a chambear");
                semDaysforDeploy.acquire();
                if (Main.DaysforDeploy == 0){
                    Main.DaysforDeploy+=loteDuration; 
                    System.out.println("Se acabo lo que se daba");
                }
                semDaysforDeploy.release();
                
            }catch(Exception e){
                System.out.println("pelo bola el boss");
            }
        }
    }
    
}