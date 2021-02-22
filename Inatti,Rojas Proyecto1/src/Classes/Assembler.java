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
            if (!stop) {
                try {
                    semAssemblerButtons.acquire(buttons);
                    mutexButtons.acquire();
                    Menu.Buttons -= buttons;
                    Menu.ButtonStorage.setText(Integer.toString(Menu.Buttons));
                    Menu.OutputConsole.setText(Menu.OutputConsole.getText() + "Retirados -> " + buttons + " botones\nBotones restantes -> " + Menu.Buttons + "\n");
                    mutexButtons.release();
                    semButtons.release(buttons);

                    semAssemblerNormalScreens.acquire(normalScreens);
                    mutexNormalScreens.acquire();
                    Menu.NormalScreens -= normalScreens;
                    Menu.NormalScreenStorage.setText(Integer.toString(Menu.NormalScreens));
                    Menu.OutputConsole.setText(Menu.OutputConsole.getText() + "Retirados -> " + normalScreens + " pantallas normales\nPantallas normales restantes -> " + Menu.NormalScreens + "\n");
                    mutexNormalScreens.release();
                    semNormalScreens.release(normalScreens);
                    
                    semAssemblerTouchScreens.acquire(touchScreens);
                    mutexTouchScreens.acquire();
                    Menu.TouchScreens -= touchScreens;
                    Menu.TouchScreenStorage.setText(Integer.toString(Menu.TouchScreens));
                    Menu.OutputConsole.setText(Menu.OutputConsole.getText() + "Retirados -> " + touchScreens + " pantallas normales\nPantallas táctiles restantes -> " + Menu.TouchScreens + "\n");
                    mutexTouchScreens.release();
                    semTouchScreens.release(touchScreens);

                    semAssemblerJoysticks.acquire(joysticks);
                    mutexJoysticks.acquire();
                    Menu.Joysticks -= joysticks;
                    Menu.JoystickStorage.setText(Integer.toString(Menu.Joysticks));
                    Menu.OutputConsole.setText(Menu.OutputConsole.getText() + "Retirados -> " + joysticks + " joysticks\nJoysticks restantes -> " + Menu.Joysticks + "\n");
                    mutexJoysticks.release();
                    semJoysticks.release(joysticks);

                    semAssemblerSDCards.acquire(SDCards);
                    mutexSDCards.acquire();
                    Menu.SDCards -= SDCards;
                    Menu.SDCardStorage.setText(Integer.toString(Menu.SDCards));
                    Menu.OutputConsole.setText(Menu.OutputConsole.getText() + "Retirados -> " + SDCards + " SD Cards\nSD Cards restantes -> " + Menu.SDCards + "\n");
                    mutexSDCards.release();
                    semSDCards.release(SDCards);
                    
                    mutexAssembler.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                    Menu.Consoles++;
                    Menu.ConsoleStorage.setText(Integer.toString(Menu.Consoles));
                    Menu.OutputConsole.setText(Menu.OutputConsole.getText() + "Consolas ensambladas -> " + Menu.Consoles + "\n");
                    mutexAssembler.release();
                } catch (Exception e) {

                }
            }
        }
    }

}
