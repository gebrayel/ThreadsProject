package inatti.rojas.proyecto1;

public class ButtonProducer extends Thread {

    private int dayDuration;
    private double dailyProduce = 2;
    public Semaphore semaphore;

    public ButtonProducer(int dayDuration, Semaphore semaphore) {
        this.dayDuration = dayDuration;
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
            if (semaphore.y < semaphore.storage) {
                try {
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                    Main.Buttons++;
                    System.out.println("Boton" + Main.Buttons);
                } catch (Exception e) {

                }
            }
        }
    }

}
