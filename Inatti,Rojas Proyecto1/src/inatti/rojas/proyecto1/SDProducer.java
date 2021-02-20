
package inatti.rojas.proyecto1;

/**
 *
 * @author diosito
 */
public class SDProducer extends Thread{
    private int dayDuration;
    private double dailyProduction = 1/3f;
    
    public SDProducer (int dayDuration){
        this.dayDuration = dayDuration;
    }
    
    public void run(){
        while(true){
            try{
                System.out.println(Math.round((dayDuration*1000)/dailyProduction));
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduction));
                Main.SD++;
                System.out.println("SD" + Main.SD);
            }catch(Exception e){
                System.out.println("SD PELO BOLA");
            }
        }
    }
}
