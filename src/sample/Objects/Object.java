package sample.Objects;

import javafx.scene.canvas.GraphicsContext;

public abstract class Object {
    //In regards to abstraction and polymorphism (eg. drawActiveObjects (Controller, l: 100))
    public abstract void draw(GraphicsContext graphicsContext);
}
