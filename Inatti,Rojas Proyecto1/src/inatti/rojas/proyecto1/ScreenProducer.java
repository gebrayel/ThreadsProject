package inatti.rojas.proyecto1;

public class ScreenProducer extends Thread{
    
    private int dayDuration;
    private double dailyProduce = 1;
    
    public ScreenProducer(int dayDuration) {
        this.dayDuration = dayDuration;
    }
    
    public void run(){
        while(true){
            try{
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduce));
                Main.NormalScreens++;
                System.out.println("Normal" + Main.NormalScreens);
                this.dailyProduce = 0.5;
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduce));
                Main.TouchScreens++;
                System.out.println("Tactil" + Main.TouchScreens);
                this.dailyProduce = 1;
            }catch(Exception e){
                
            }
        }
    }
    
}
