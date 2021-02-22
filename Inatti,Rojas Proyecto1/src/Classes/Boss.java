package Classes;

import Windows.*;
import java.util.concurrent.Semaphore;

public class Boss extends Thread {
    
    private int dayDuration;
    private double sleepTime=24/18f;
    private double workingTime=24/6f;
    private Semaphore semDaysForDispatch;
    
    public Boss(int dayDuration, Semaphore semDaysForDispatch){
        
        this.dayDuration = dayDuration;
        this.semDaysForDispatch = semDaysForDispatch;
        
    }
    
    public void run(){
        while(true){
            try{
                Menu.BossStatus.setText("😴");
                Thread.sleep(Math.round((dayDuration*1000)/sleepTime));
                semDaysForDispatch.acquire();
                Menu.BossStatus.setText("Chambea");
                Thread.sleep(Math.round((dayDuration*1000)/workingTime));
                Menu.currentDispatch--;
                Menu.CurrentDispatch.setText(Integer.toString(Menu.currentDispatch));
                semDaysForDispatch.release();
                
            }catch(Exception e){
                System.out.println("pelo bola el boss");
            }
        }
    }
    
}
