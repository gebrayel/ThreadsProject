package Classes;

import Windows.*;
import java.util.concurrent.Semaphore;

public class Manager extends Thread {
    
    private int dayDuration;
    private int loteDuration;
    private boolean stop;
    private double sleepTime=24/2f;
    private Semaphore semDaysforDeploy;
    public Manager(int dayDuration, int loteDuration, Semaphore semDaysforDeploy){
        
        this.dayDuration = dayDuration;
        this.stop = false;
        this.semDaysforDeploy = semDaysforDeploy;
        this.loteDuration = loteDuration;
    }
    
    public void run(){
        while(!this.stop){
            
            try{
                Menu.ManagerStatus.setText("Mimido");
                Thread.sleep(Math.round((dayDuration*1000)/sleepTime));
                semDaysforDeploy.acquire();
                Menu.ManagerStatus.setText("Chambea");
                if (Menu.currentDispatch == 0){
                    Menu.currentDispatch += loteDuration; 
                    Menu.TotalConsoles += Menu.Consoles;
                    Menu.TotalConsole.setText(Integer.toString(Menu.TotalConsoles));
                    Menu.OutputConsoles.setText("<-Despliegue de " + Menu.Consoles + "->\n");
                    Menu.Consoles = 0;
                    Menu.ConsoleStorage.setText(Integer.toString(Menu.Consoles));
                }
                semDaysforDeploy.release();
            }catch(Exception e){
                System.out.println("pelo bola el boss");
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