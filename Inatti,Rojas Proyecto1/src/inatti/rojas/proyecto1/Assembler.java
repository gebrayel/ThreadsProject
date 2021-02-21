package inatti.rojas.proyecto1;

public class Assembler extends Thread {
  
    private int dayDuration;
    private double dailyProduce = 1;
    
    public Assembler(int dayDuration) {
        this.dayDuration = dayDuration;
    }
    
    public void run(){
        while(true){
            try{
                if (Main.Buttons >= 5 && Main.NormalScreens >= 1 && Main.TouchScreens >= 1) {
                    Thread.sleep(Math.round((dayDuration*1000)/dailyProduce));
                    Main.Buttons -= 5;
                    Main.NormalScreens--;
                    Main.TouchScreens--;
                    Main.Consolas++;
                    System.out.println("Consolas" + Main.Consolas);
                }
            }catch(Exception e){
                
            }
        }
    }
    
}
