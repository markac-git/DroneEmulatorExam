package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import sample.Objects.Drone;
import sample.Objects.Object;
import sample.Objects.Scene;
import sample.UDP.UdpBroadcastServer;
import sample.UDP.UdpConnector;
import sample.UDP.UdpMessage;

import java.util.ArrayList;


public class Controller {

    @FXML // Necessary FXML elements
    TableView<UdpMessage> table;
    ToggleButton toggleBtnEcho;
    ToggleButton toggleBtnBroadcast;
    ToggleButton buttonPowerOn;

    private UdpConnector udpConnector; private UdpBroadcastServer broadcastServer;
    public Canvas canvas; private GraphicsContext graphicsContext; private Updater updater;
    private Drone drone; private final ArrayList<Object> objects = new ArrayList<>(); private Scene scene;

    public void initialize() throws InterruptedException {
        //creating instances
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.drone = new Drone(canvas.getWidth(),canvas.getHeight());
        this.scene = new Scene();

        //Starting threads
        startUdpConnection();
        startBroadcasting();
        startUpdater();

        //Adding objects to the arraylist
        objects.add(scene);
        objects.add(drone);
        objects.add(drone.getBattery());
    }

    public void startDrone() {
            new Thread(drone).start();
    }

    private void startBroadcasting() {
        //creating instance
        this.broadcastServer = new UdpBroadcastServer();
        //starting new thread calling the run method
        //new Thread(broadcastServer).start();
    }

    private void startUdpConnection() {
        if (udpConnector != null) udpConnector.closeSocket();
        udpConnector = new UdpConnector(this);
        new Thread(udpConnector).start();
    }

    private void startUpdater() {
        updater  = new Updater(this);
        new Thread(updater).start();
    }

    public void setToggleBG(){
        scene.toggleBG();
    }

    public void toggleBtnEchoServer() {
        System.out.println("togglebtnECHO clicked");
        if (udpConnector.isReceiveMessages()) {
            udpConnector.setReceiveMessages(false);
            toggleBtnEcho.setText("OFF");
        }
        else {
            startUdpConnection();
            toggleBtnEcho.setText("ON");
        }
    }

    public void toggleBtnBroadcastServer() {
        System.out.println("togglebtnBROADCAST clicked");
        if (broadcastServer.isBroadcast()) {
            broadcastServer.setBroadcast(false);
            toggleBtnBroadcast.setText("OFF");
        } else {
            startBroadcasting();
            toggleBtnBroadcast.setText(("ON"));
        }
    }

    public void clearLog() {
        table.getItems().clear();
    }

    public void receiveMessage(UdpMessage udpMessage) throws InterruptedException {
        table.getItems().add(0, udpMessage);
        moveObject(udpMessage); //calling controls with udpMessage as parameter when receiving message
    }

    void drawActiveObjects(ArrayList<Object> objects) {
        //In regards to polymorphism. As every object is of type Object it enables us doing following
        for (Object o : objects) {
            o.draw(graphicsContext);
        }
    }

    private void moveObject(UdpMessage udpMessage)  {
            //called from receiveMessage() method
            broadcastServer.broadcastMessage(drone.controls(udpMessage));
    }

    public void updateCanvas() throws InterruptedException {
            graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            //System.out.println("cleared");
            drawActiveObjects(objects);
            //System.out.println("drawn");
            Thread.sleep(100);
    }
}
