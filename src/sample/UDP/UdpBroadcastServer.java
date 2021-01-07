package sample.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpBroadcastServer /*implements Runnable*/ {
    private boolean broadcast = true;
    private DatagramSocket socket;
    private int portBroadcast;
    //public String message = "Echoserver is ready on port 7000";

    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public void broadcastMessage(String message) {
        String target = "192.168.0.123";
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(target), 8005);
            socket.send(packet);
            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

       /* @Override
    public void run() {
        //broadcastLoop();
    }*/

   /* public void broadcastLoop() {
        do {
            try {
                Thread.sleep((3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //if (broadcast) broadcastMessage(message);
        }
        while (broadcast);
    }*/
}