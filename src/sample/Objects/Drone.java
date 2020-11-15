package sample.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.UDP.UdpMessage;

public class Drone extends Object implements Runnable{
    private double latitude,longitude;
    private final double size = 100;
    private final double canvasW;
    private final double canvasH;
    private final double velocity=0.00001;
    private Battery battery;
    private final Image telloImg1;
    private final Image telloImg2;
    private Image telloImg;
    private boolean powerOn;

    public Drone(double canvasW, double canvasH) {
        this.battery = new Battery(100);
        double centerX = canvasW/2;
        this.canvasH = canvasH;
        this.canvasW = canvasW;
        this.latitude = centerX;
        this.longitude = canvasH;
        this.telloImg1 = new Image(getClass().getResourceAsStream("tello1.png"));
        this.telloImg2 = new Image(getClass().getResourceAsStream("tello2.png"));
        this.telloImg = telloImg1;
    }


    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getSize() {
        return size;
    }
    public double getCanvasW() {
        return canvasW;
    }
    public double getCanvasH() {
        return canvasH;
    }
    public double getVelocity() {
        return velocity;
    }
    public Battery getBattery() {
        return battery;
    }

    public void setTelloImg(Image telloImg) {
        this.telloImg = telloImg;
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    public void setPowerOn(boolean powerOn) {
        this.powerOn = powerOn;
    }

    public void usePower(int batteryUsage){
        this.battery.setPower(this.battery.getPower()-batteryUsage);
    }


    public void draw(GraphicsContext graphicsContext) {
        /* TEST DRAWING RECTANGLE
        graphicsContext.setStroke(Color.RED);
        graphicsContext.setLineWidth(10);
        graphicsContext.strokeLine(getLatitude()-size, getLongitude(), getLatitude()+size,getLongitude());*/
        graphicsContext.drawImage(telloImg, latitude,longitude-size,size,size);
    }


    public String controls(UdpMessage udpMessage) {
        String command = udpMessage.getMessage();
        int value = 0;
        final double tempLongitude = getLongitude();
        final double tempLatitude = getLatitude();

        if (!powerOn) {
            return "Please turn on power before sending commands";
        } else {
            //using substrings to get last two chars storing it as a value
            if (command.startsWith("up") || command.startsWith("down") || command.startsWith("left") || command.startsWith("right")) {
                value = Integer.parseInt(command.substring(command.length() - 2));
                command = command.substring(0, command.length()-2);
            }

            //switch statement that executes a case depending on input
            switch (command) {
                case "takeoff":
                    while (getLongitude() > getCanvasH() / 2 + size) {
                        longitude-= 0.01 * getVelocity();
                    }
                    usePower(10);
                    break;
                case "land":
                    while (getLongitude() < getCanvasH()){
                        longitude+= 0.01 * getVelocity();
                    }
                    usePower(10);
                    break;
                case "battery":
                    return "Battery: " + getBattery().getPower();
                case "up":
                    while (getLongitude()>tempLongitude-value){
                        longitude-=0.01*getVelocity();
                    }
                    usePower(5);
                    break;
                case "down":
                    while (getLongitude()<tempLongitude+value){
                        longitude+=0.01*getVelocity();
                    }
                    usePower(5);
                    break;
                case "left":
                    while (getLatitude()>tempLatitude-value){
                        latitude-=0.01*getVelocity();
                    }
                    usePower(5);
                    break;
                case "right":
                    while (getLatitude()<tempLatitude+value){
                        latitude+=0.01*getVelocity();
                    }
                    usePower(5);
                    break;
                default:
                    return "Command not recognized...";
            }
            return "OK";
        }
    }

    @Override
    public void run() {
        //Running in its own thread
        try {
            turnOn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void turnOn() throws InterruptedException {
        if (!isPowerOn()){
            setPowerOn(true);
            startEngine();
        } else {
            if (getLongitude()>getCanvasH()-getSize()){
                setPowerOn(false);
            }
            /*Above is a workaround of avoiding interference with while loop (This class, line: )
            while (longitude<canvasH){
                setLongitude(getLongitude() + 0.01 * velocity);
            }*/
        }
    }

    private void startEngine() throws InterruptedException {
        //simulates movement of propels while isPowerOn
        while (isPowerOn()) {
            setTelloImg(telloImg1);
            Thread.sleep(100);
            setTelloImg(telloImg2);
            Thread.sleep(100);
        }
    }
}