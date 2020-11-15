package sample;

public class Updater implements Runnable{
    Controller controller;

    public Updater (Controller controller)
    {
        this.controller = controller;
    }

    public void run() {
        while (true){
            try {
                controller.updateCanvas(); //clears and draws objects
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(0,5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
