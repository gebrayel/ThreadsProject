
package inatti.rojas.proyecto1;

public class JoystickProducer extends Thread{
    
    private int dayDuration;
    private double dailyProduction = 0.5;
    
    public JoystickProducer (int dayDuration){
        this.dayDuration = dayDuration;
    }
    
    public void run(){
        while(true){
            try{
                Thread.sleep(Math.round((dayDuration*1000)/dailyProduction));
                Main.Joysticks++;
                System.out.println("Joystick" + Main.Joysticks);
            }catch(Exception e){
                System.out.println("Joystick pelo bola");
            }
        }
    }
}
