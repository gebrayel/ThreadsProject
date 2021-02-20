package inatti.rojas.proyecto1;

public class Main {

    public static volatile int Buttons = 0;
    public static volatile int TouchScreens = 0;
    public static volatile int NormalScreens = 0;
    public static volatile int Joysticks=0;
    public static volatile int SD=0;
    
    public static void main(String[] args) {
        
        int dayDuration = 4;
        
        ButtonProducer button = new ButtonProducer(dayDuration);
        ScreenProducer screen = new ScreenProducer(dayDuration);
        JoystickProducer joystick = new JoystickProducer(dayDuration);
        SDProducer sd = new SDProducer(dayDuration);
        
        button.start();
        screen.start();
        joystick.start();
        sd.start();
        
    }
    
}
