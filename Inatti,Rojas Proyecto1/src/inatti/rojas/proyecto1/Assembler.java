package inatti.rojas.proyecto1;
import java.util.concurrent.Semaphore;

public class Assembler extends Thread {

    private int dayDuration;
    private boolean stop;
    private double dailyProduce = 1;
    private int buttons = 5;
    private int normalScreens = 1;
    private int touchScreens = 1;
    private int joysticks = 2;
    private int SDs = 1;
    private Semaphore mutexEnsamblador;
    
    private Semaphore mutexBotones;
    private Semaphore semEnsambladorBotones;
    
    private Semaphore mutexNormalScreens;
    private Semaphore semEnsambladorNormalScreens;
    
    private Semaphore mutexTouchScreens;
    private Semaphore semEnsambladorTouchScreens;
    
    private Semaphore mutexJoysticks;
    private Semaphore semEnsambladorJoysticks;
    
    private Semaphore mutexSD;
    private Semaphore semEnsambladorSD;
    
    private Semaphore semConsolas;

    public Assembler(int dayDuration, Semaphore mutexEnsamblador, 
                    Semaphore mutexBotones, Semaphore semEnsambladorBotones, 
                    Semaphore mutexNormalScreens, Semaphore semEnsambladorNormalScreens,
                    Semaphore mutexTouchScreens, Semaphore semEnsambladorTouchScreens, 
                    Semaphore mutexJoysticks, Semaphore semEnsambladorJoysticks, 
                    Semaphore mutexSD, Semaphore semEnsambladorSD, 
                    Semaphore semConsolas) {
        
        this.dayDuration = dayDuration;
        this.mutexEnsamblador = mutexEnsamblador;
        
        this.mutexBotones = mutexBotones;
        this.semEnsambladorBotones = semEnsambladorBotones;
        
        this.mutexNormalScreens = mutexNormalScreens;
        this.semEnsambladorNormalScreens = semEnsambladorNormalScreens;
        
        this.mutexTouchScreens = mutexTouchScreens;
        this.semEnsambladorTouchScreens = semEnsambladorTouchScreens;
        
        this.mutexJoysticks = mutexJoysticks;
        this.semEnsambladorJoysticks = semEnsambladorJoysticks;
        
        this.mutexSD = mutexSD;
        this.semEnsambladorSD = semEnsambladorSD;
        
        this.semConsolas = semConsolas;
    }

    public void run() {
        while (true) {
            if (!stop) {
                try {
                    semEnsambladorBotones.acquire(buttons);
                    mutexBotones.acquire();
                    Main.Buttons-=buttons;
                    mutexBotones.release();
                    
                    semEnsambladorNormalScreens.acquire(normalScreens);
                    mutexNormalScreens.acquire();
                    Main.NormalScreens-=normalScreens;
                    mutexNormalScreens.release();
                    
                    semEnsambladorTouchScreens.acquire(touchScreens);
                    mutexTouchScreens.acquire();
                    Main.TouchScreens-=touchScreens;
                    mutexTouchScreens.release();
                    
                    semEnsambladorJoysticks.acquire(joysticks);
                    mutexJoysticks.acquire();
                    Main.Joysticks-=joysticks;
                    mutexJoysticks.release();
                    
                    semEnsambladorSD.acquire(SDs);
                    mutexSD.acquire();
                    Main.SD -= SDs;
                    mutexSD.release();
                    
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
