package inatti.rojas.proyecto1;

public class ButtonProducer extends Thread{
    
    private int dayDuration;
    private int dailyProducte = 2;
    
    public ButtonProducer(int dayDuration) {
        this.dayDuration = dayDuration;
    }
    
    public void run(){
        while(true){
            try{
                Thread.sleep((dayDuration*1000)/2);
                Main.Buttons++;
                System.out.println(Main.Buttons);
            }catch(Exception e){
                
            }
        }
    }
    
}
