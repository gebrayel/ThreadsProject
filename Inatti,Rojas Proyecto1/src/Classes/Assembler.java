package Classes;

import Windows.*;
import java.util.concurrent.Semaphore;

public class Assembler extends Thread {

    private int dayDuration;
    private boolean stop;
    private double dailyProduce = 1;
    private int buttons = 5;
    private int normalScreens = 1;
    private int touchScreens = 1;
    private int joysticks = 2;
    private int SDCards = 1;
    private Semaphore mutexAssembler;

    private Semaphore mutexButtons;
    private Semaphore semButtons;
    private Semaphore semAssemblerButtons;

    private Semaphore mutexNormalScreens;
    private Semaphore semNormalScreens;
    private Semaphore semAssemblerNormalScreens;

    private Semaphore mutexTouchScreens;
    private Semaphore semTouchScreens;
    private Semaphore semAssemblerTouchScreens;

    private Semaphore mutexJoysticks;
    private Semaphore semJoysticks;
    private Semaphore semAssemblerJoysticks;

    private Semaphore mutexSDCards;
    private Semaphore semSDCards;
    private Semaphore semAssemblerSDCards;

    public Assembler(int dayDuration, Semaphore mutexAssembler,
            Semaphore mutexButtons, Semaphore semButtons, Semaphore semAssemblerButtons,
            Semaphore mutexNormalScreens, Semaphore semNormalScreens, Semaphore semAssemblerNormalScreens,
            Semaphore mutexTouchScreens, Semaphore semTouchScreens, Semaphore semAssemblerTouchScreens,
            Semaphore mutexJoysticks, Semaphore semJoysticks, Semaphore semAssemblerJoysticks,
            Semaphore mutexSDCards, Semaphore semSDCards, Semaphore semAssemblerSDCards) {

        this.dayDuration = dayDuration;
        this.mutexAssembler = mutexAssembler;

        this.mutexButtons = mutexButtons;
        this.semButtons = semButtons;
        this.semAssemblerButtons = semAssemblerButtons;

        this.mutexNormalScreens = mutexNormalScreens;
        this.semNormalScreens = semNormalScreens;
        this.semAssemblerNormalScreens = semAssemblerNormalScreens;

        this.mutexTouchScreens = mutexTouchScreens;
        this.semTouchScreens = semTouchScreens;
        this.semAssemblerTouchScreens = semAssemblerTouchScreens;

        this.mutexJoysticks = mutexJoysticks;
        this.semJoysticks = semJoysticks;
        this.semAssemblerJoysticks = semAssemblerJoysticks;

        this.mutexSDCards = mutexSDCards;
        this.semSDCards = semSDCards;
        this.semAssemblerSDCards = semAssemblerSDCards;
    }

    public void run() {
        while (true) {
            if (!this.stop) {
                try {
                    semAssemblerButtons.acquire(buttons);
                    
                    semAssemblerNormalScreens.acquire(normalScreens);
                    
                    semAssemblerTouchScreens.acquire(touchScreens);

                    semAssemblerJoysticks.acquire(joysticks);

                    semAssemblerSDCards.acquire(SDCards);
                    
                    mutexButtons.acquire();
                    Menu.Buttons -= buttons;
                    Menu.ButtonStorage.setText(Integer.toString(Menu.Buttons));
                    Menu.OutputButtons.setText(Menu.OutputButtons.getText() + "Retirados -> " + buttons + " botones\nBotones restantes -> " + Menu.Buttons + "\n");
                    mutexButtons.release();
                    semButtons.release(buttons);

                    mutexNormalScreens.acquire();
                    Menu.NormalScreens -= normalScreens;
                    Menu.NormalScreenStorage.setText(Integer.toString(Menu.NormalScreens));
                    Menu.OutputNormalScreens.setText(Menu.OutputNormalScreens.getText() + "Retirados -> " + normalScreens + " pantallas normales\nPantallas normales restantes -> " + Menu.NormalScreens + "\n");
                    mutexNormalScreens.release();
                    semNormalScreens.release(normalScreens);
                    
                    mutexTouchScreens.acquire();
                    Menu.TouchScreens -= touchScreens;
                    Menu.TouchScreenStorage.setText(Integer.toString(Menu.TouchScreens));
                    Menu.OutputTouchScreens.setText(Menu.OutputTouchScreens.getText() + "Retirados -> " + touchScreens + " pantallas normales\nPantallas táctiles restantes -> " + Menu.TouchScreens + "\n");
                    mutexTouchScreens.release();
                    semTouchScreens.release(touchScreens);

                    mutexJoysticks.acquire();
                    Menu.Joysticks -= joysticks;
                    Menu.JoystickStorage.setText(Integer.toString(Menu.Joysticks));
                    Menu.OutputJoysticks.setText(Menu.OutputJoysticks.getText() + "Retirados -> " + joysticks + " joysticks\nJoysticks restantes -> " + Menu.Joysticks + "\n");
                    mutexJoysticks.release();
                    semJoysticks.release(joysticks);

                    mutexSDCards.acquire();
                    Menu.SDCards -= SDCards;
                    Menu.SDCardStorage.setText(Integer.toString(Menu.SDCards));
                    Menu.OutputSDCards.setText(Menu.OutputSDCards.getText() + "Retirados -> " + SDCards + " SD Cards\nSD Cards restantes -> " + Menu.SDCards + "\n");
                    mutexSDCards.release();
                    semSDCards.release(SDCards);
                    
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                    mutexAssembler.acquire();
                    Menu.Consoles++;
                    Menu.ConsoleStorage.setText(Integer.toString(Menu.Consoles));
                    if (Menu.OutputConsoles.getText().split("\n").length != 10) {
                        Menu.OutputConsoles.setText(Menu.OutputConsoles.getText() + "Consolas -> " + Menu.Consoles + "\n");
                    }else{
                        Menu.OutputConsoles.setText("Consolas -> " + Menu.Consoles + "\n");
                    }
                    mutexAssembler.release();
                } catch (Exception e) {

                }
            }
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

}