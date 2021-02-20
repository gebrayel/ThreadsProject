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
                if (Main.Buttons >= 5 && Main.NormalScreen >= 1 && Main.TouchScreen >= 1) {
                    Thread.sleep(Math.round((dayDuration*1000)/dailyProduce));
                    Main.Buttons -= 5;
                    Main.NormalScreen--;
                    Main.TouchScreen--;
                    Main.Consoles++;
                    System.out.println("Consolas" + Main.Consoles);
                }
            }catch(Exception e){
                
            }
        }
    }
    
}
