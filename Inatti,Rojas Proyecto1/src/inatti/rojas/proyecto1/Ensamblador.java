package inatti.rojas.proyecto1;

import java.util.concurrent.Semaphore;

public class Ensamblador extends Thread {

    private int dayDuration;
    private int dailyProduction = 1;

    public Ensamblador(int dayDuration, Semaphore mutexEnsamblador, 
            Semaphore semBotones, Semaphore semScreens, Semaphore semJoysticks, 
            Semaphore semEnsambladorSD) {
        this.dayDuration = dayDuration;
    }
    public void run(){
        while (true){
            try{
                System.out.println("im the assembler");
                Thread.sleep((dayDuration*1000)/dailyProduction);
            }catch(Exception e){
                System.out.println("Assembler PELO BOLA");
            }
            
        }
    }
}
