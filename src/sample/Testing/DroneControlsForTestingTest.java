package sample.Testing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DroneControlsForTestingTest {
    DroneControlsForTesting drone = new DroneControlsForTesting(100,100);
    @Test
    void controls() {
        String s;
        s="takeoff";
        assertEquals("Please turn on power before sending commands",drone.controls(s));
        drone.setPowerOn(true);
        s="takeoff";
        assertEquals(s,drone.controls(s));
        s="land";
        assertEquals(s,drone.controls(s));
        s="up30";
        assertEquals(s,drone.controls(s));
        s="down30";
        assertEquals(s,drone.controls(s));
        s="left30";
        assertEquals(s,drone.controls(s));
        s="right30";
        assertEquals(s,drone.controls(s));
        s="battery";
        assertEquals("Battery: 100",drone.controls(s));
        s="asd";
        assertEquals("Command not recognized...",drone.controls(s));
    }
}