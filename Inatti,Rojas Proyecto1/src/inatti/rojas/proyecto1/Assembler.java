package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;

public class Assembler extends Thread {

    private int dayDuration;
    private boolean stop;
    private double dailyProduce = 1;
    private int buttons = 5;
    private int normalScreen = 1;
    private int touchScreen = 1;
    private int joystick = 2;
    private int SD = 1;
    private Semaphore mutexEnsamblador;
    
    private Semaphore mutexBotones;
    private Semaphore semEnsambladorBotones;
    
    private Semaphore mutexNormalScreens;
    private Semaphore semEnsambladorNormalScreens;
    
    private Semaphore mutexTouchScreens;
    private Semaphore semEnsambladorTouchScreens;
    
    private Semaphore semConsolas;

    public Assembler(int dayDuration, Semaphore mutexEnsamblador, 
                    Semaphore mutexBotones, Semaphore semEnsambladorBotones, 
                    Semaphore mutexNormalScreens, Semaphore semEnsambladorNormalScreens,
                    Semaphore mutexTouchScreens, Semaphore semEnsambladorTouchScreens, 
                    Semaphore semConsolas) {
        
        this.dayDuration = dayDuration;
        this.mutexEnsamblador = mutexEnsamblador;
        
        this.mutexBotones = mutexBotones;
        this.semEnsambladorBotones = semEnsambladorBotones;
        
        this.mutexNormalScreens = mutexNormalScreens;
        this.semEnsambladorNormalScreens = semEnsambladorNormalScreens;
        
        this.mutexTouchScreens = mutexTouchScreens;
        this.semEnsambladorTouchScreens = semEnsambladorTouchScreens;
        
        this.semConsolas = semConsolas;
    }

    public void run() {
        while (true) {
            if (!stop) {
                try {
                    semEnsambladorBotones.acquire(buttons);
                    mutexBotones.acquire();
                    Main.Buttons-=5;
                    mutexBotones.release();
                    
                    semEnsambladorNormalScreens.acquire(normalScreen);
                    mutexNormalScreens.acquire();
                    Main.NormalScreens--;
                    mutexNormalScreens.release();
                    
                    semEnsambladorTouchScreens.acquire(touchScreen);
                    mutexTouchScreens.acquire();
                    Main.TouchScreens--;
                    mutexTouchScreens.release();
                    
                    
                    mutexEnsamblador.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                    Main.Consolas++;
                    System.out.println("Consolas" + Main.Consolas);
                    mutexEnsamblador.release();
                    semConsolas.release();
                } catch (Exception e) {

                }
            }
        }
    }

}
