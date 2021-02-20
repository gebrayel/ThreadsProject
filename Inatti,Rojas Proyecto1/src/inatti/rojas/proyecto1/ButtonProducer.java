package inatti.rojas.proyecto1;

public class ButtonProducer extends Thread{
    
    private int dayDuration;
    private double dailyProduce = 2;
    
    public ButtonProducer(int dayDuration) {
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
