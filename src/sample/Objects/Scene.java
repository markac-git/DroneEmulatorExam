package sample.Objects;

import java.awt.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Scene extends Object{

    private Image bg;
    private Image day = new Image(getClass().getResourceAsStream("bgDay.png"));
    private Image night = new Image(getClass().getResourceAsStream("bgNight.png"));

    public Scene() {
        this.bg = day;
    }

    public Image getBg() {
        return bg;
    }

    public void setBg(Image bg) {
        this.bg = bg;
    }

    public void toggleBG(){
        if (getBg().equals(day)){
            setBg(night);
        } else {
            setBg(day);
        }
    }


    @Override
    public void draw(GraphicsContext graphicsContext) {
        double canvasH = graphicsContext.getCanvas().getHeight();
        double canvasW = graphicsContext.getCanvas().getWidth();

        graphicsContext.drawImage(bg,0,0,canvasW,canvasH);
    }
}
