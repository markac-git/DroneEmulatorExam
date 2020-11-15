package sample.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Objects.Object;

public class Battery extends Object {

    private int power;

    public Battery(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void draw(GraphicsContext graphicsContext) {
        //illustrating battery on canvas as a rectangle
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(30,40, getPower(), 20);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText("Battery",30,30);
    }
}
