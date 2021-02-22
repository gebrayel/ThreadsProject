package Classes;

import Windows.*;
import java.util.concurrent.Semaphore;

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
                Menu.ManagerStatus.setText("Mimido");
                Thread.sleep(Math.round((dayDuration*1000)/sleepTime));
                semDaysforDeploy.acquire();
                Menu.ManagerStatus.setText("Chambea");
                if (Menu.currentDispatch == 0){
                    Menu.currentDispatch += loteDuration; 
                    Menu.TotalConsoles += Menu.Consoles;
                    Menu.TotalConsole.setText(Integer.toString(Menu.TotalConsoles));
                    Menu.OutputConsole.setText("<-Despliegue de " + Menu.Consoles + "->\n");
                    Menu.Consoles = 0;
                    Menu.ConsoleStorage.setText(Integer.toString(Menu.Consoles));
                }
                semDaysforDeploy.release();
            }catch(Exception e){
                System.out.println("pelo bola el boss");
            }
        }
    }
    
}