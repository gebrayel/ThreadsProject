package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;

public class ButtonProducer extends Thread{
    
    private int dayDuration;
    private double dailyProduce = 2;
    
    public ButtonProducer(Semaphore mutex, Semaphore semBotones, int dayDuration, Semaphore semEnsamblador) {
        this.dayDuration = dayDuration;
    }
    
    public void run(){
        while(true){
            try{
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduce));
                Main.Buttons++;
                System.out.println("Boton" + Main.Buttons);
            }catch(Exception e){
                
            }
        }
    }
    
}
