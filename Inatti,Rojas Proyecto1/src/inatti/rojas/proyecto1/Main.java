package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;
public class Main {

    /*CONTADORES DE PIEZAS*/
    public static volatile int Buttons = 0;
    public static volatile int TouchScreens = 0;
    public static volatile int NormalScreens = 0;
    public static volatile int Joysticks=0;
    public static volatile int SD=0;
    public static volatile int Consolas=0;
    
    /*DIAS PARA LANZAMIENTO QUE MODIFICA EL JEFE*/
    public static volatile int DaysforDeploy = 30;
    
    /*Arreglo de hilos trabajando; los hilos que se vayan ejecutando se van añadiendo o quitando de este arreglo
    de acuerdo a la opcion que se indique por interfaz, y nunca pasa el limite de maximos productores que se indique
    por txt, ya que con ese dato se declara la longitud del arreglo*/
    static ButtonProducer arrayButtons [];
    static ScreenProducer arrayScreens [];
    static JoystickProducer arrayJoysticks [];
    static SDProducer arraySD [];
    static Ensamblador arrayEnsamblador [];
    
    /*Numero de productores maximos*/
    static int buttonProducerNum = 1;
    static int ScreenProducerNum = 1;
    static int JoystickProducerNum = 1;
    static int SDProducerNum = 1;
    static int EnsambladorNum = 1;
    
    public static void main(String[] args) {
        
        int dayDuration = 4; /*Segundos que durara el dia*/
        
        
        arrayButtons = new ButtonProducer[buttonProducerNum];
        arrayScreens = new ScreenProducer[ScreenProducerNum];
        arrayJoysticks = new JoystickProducer[JoystickProducerNum];
        arraySD = new SDProducer[SDProducerNum];
        arrayEnsamblador = new Ensamblador[EnsambladorNum];
        
        Semaphore mutexBotones = new Semaphore(1);
        Semaphore mutexScreens = new Semaphore(1);
        Semaphore mutexJoysticks = new Semaphore(1);
        Semaphore mutexSD = new Semaphore(1);
        Semaphore mutexEnsamblador = new Semaphore(1);
        Semaphore semBotones = new Semaphore(1);
        Semaphore semScreens = new Semaphore(1);
        Semaphore semJoysticks = new Semaphore(1);
        Semaphore semSD = new Semaphore(1);
        Semaphore semEnsambladorBotones = new Semaphore(1);
        Semaphore semEnsambladorScreens = new Semaphore(1);
        Semaphore semEnsambladorJoysticks = new Semaphore(1);
        Semaphore semEnsambladorSD = new Semaphore(1);
        
        ButtonProducer button = new ButtonProducer(mutexBotones, semBotones, dayDuration, semEnsambladorBotones);
        ScreenProducer screen = new ScreenProducer(mutexScreens, semScreens, dayDuration, semEnsambladorScreens);
        JoystickProducer joystick = new JoystickProducer(mutexJoysticks, semJoysticks, dayDuration, semEnsambladorJoysticks);
        SDProducer sd = new SDProducer(mutexSD, semSD, dayDuration, semEnsambladorSD);
        Ensamblador ensamblador = new Ensamblador (dayDuration, mutexEnsamblador, semBotones, semScreens,semJoysticks,semEnsambladorSD);
        
        button.start();
        screen.start();
        joystick.start();
        sd.start();
        ensamblador.start();
        
    }
    
}
