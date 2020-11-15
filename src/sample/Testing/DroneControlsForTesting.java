package sample.Testing;

import javafx.scene.image.Image;
import sample.Objects.Battery;
import sample.UDP.UdpMessage;

public class DroneControlsForTesting {

    private double latitude,longitude;
    private final double size = 100;
    private final double canvasW;
    private final double canvasH;
    private final double velocity=0.00001;
    private Battery battery;
    private Image telloImg;
    private boolean powerOn;

    public DroneControlsForTesting(double canvasW, double canvasH) {
        this.battery = new Battery(100);
        double centerX = canvasW/2;
        this.canvasH = canvasH;
        this.canvasW = canvasW;
        this.latitude = centerX;
        this.longitude = canvasH;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Image getTelloImg() {
        return telloImg;
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

    public String controls(String udpMessage) {
        String command = udpMessage;
        int value = 0;
        final double tempLongitude = getLongitude();
        final double tempLatitude = getLatitude();
        String s = "error";

        if (!powerOn) {
            return "Please turn on power before sending commands";
        } else {
            if (command.startsWith("up") || command.startsWith("down") || command.startsWith("left") || command.startsWith("right")) {
                value = Integer.parseInt(command.substring(command.length() - 2));
                command = command.substring(0, command.length()-2);
            }

            switch (command) {
                case "takeoff":
                    while (longitude > canvasH / 2 + size) {
                        setLongitude(getLongitude() - 0.01 * velocity);
                    }
                    s="takeoff";
                    break;
                case "land":
                    while (getLongitude() < canvasH){
                        setLongitude(getLongitude() + 0.01 * velocity);
                    }
                    s="land";
                    break;
                case "battery":
                    return "Battery: " + getBattery().getPower();
                case "up":
                    while (longitude>tempLongitude-value){
                        longitude-=0.01*velocity;
                    }
                    s="up"+value;
                    break;
                case "down":
                    while (longitude<tempLongitude+value){
                        longitude+=0.01*velocity;
                    }
                    s="down"+value;
                    break;
                case "left":
                    while (latitude>tempLatitude-value){
                        latitude-=0.01*velocity;
                    }
                    s="left"+value;
                    break;
                case "right":
                    while (latitude<tempLatitude+value){
                        latitude+=0.01*velocity;
                    }
                    s="right"+value;
                    break;
                default:
                    s= "Command not recognized...";
            }
        }
        return s;
    }
}
