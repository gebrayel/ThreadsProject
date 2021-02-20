
package inatti.rojas.proyecto1;

public class JoystickProducer extends Thread{
    
    private int dayDuration;
    private double dailyProduction = 0.5;
    
    public JoystickProducer (int dayDuration){
        this.dayDuration = dayDuration;
    }
}
