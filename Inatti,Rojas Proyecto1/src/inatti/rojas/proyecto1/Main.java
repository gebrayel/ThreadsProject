package inatti.rojas.proyecto1;

public class Main {

    public static volatile int Buttons = 0;
    public static volatile int TouchScreen = 0;
    public static volatile int NormalScreen = 0;
    
    public static void main(String[] args) {
        
        int dayDuration = 4;
        
        ButtonProducer button = new ButtonProducer(dayDuration);
        ScreenProducer screen = new ScreenProducer(dayDuration);
        
        button.start();
        screen.start();
        
    }
    
}
