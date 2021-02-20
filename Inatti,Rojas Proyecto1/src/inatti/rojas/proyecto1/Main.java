package inatti.rojas.proyecto1;

public class Main {

    public static volatile int Buttons = 0;
    
    public static void main(String[] args) {
        
        int dayDuration = 4;
        
        ButtonProducer hilo = new ButtonProducer(dayDuration);
        hilo.start();
        
    }
    
}
