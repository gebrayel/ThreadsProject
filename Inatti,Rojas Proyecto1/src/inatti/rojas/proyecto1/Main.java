package inatti.rojas.proyecto1;

public class Main {

    public static volatile int Buttons = 0;
    public static volatile int TouchScreen = 0;
    public static volatile int NormalScreen = 0;
    public static volatile int Consoles = 0;
    
    public static void main(String[] args) {
        
        int dayDuration = 4;
        
        Semaphore semaphore = new Semaphore(3);
        
        ButtonProducer button = new ButtonProducer(dayDuration, semaphore);
        ScreenProducer screen = new ScreenProducer(dayDuration);
        Assembler assembler = new Assembler(dayDuration);
        
        
        button.start();
        screen.start();
        assembler.start();
        
    }
    
}
