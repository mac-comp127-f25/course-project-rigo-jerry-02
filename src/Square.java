import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;


public class Square {
    public static final double SIZE = 40;
    private Rectangle rect;
    private int lane; 
    private double speed;


     public Square(int lane, double x, double y, double speed, CanvasWindow canvas) {
        this.lane = lane;
        this.speed = speed;

        rect = new Rectangle(x, y, SIZE, SIZE);
        rect.setFillColor(Color.WHITE);
        rect.setFilled(true);
        rect.setStroked(false);
        canvas.add(rect);
    }

     public void update() {
        rect.setY(rect.getY() + speed);
    }

    public double getY() {
        return rect.getY();
    }

    public int getLane() {
        return lane;
    }

    public Rectangle getShape() {
        return rect;
    }


    public void removeFromCanvas(CanvasWindow canvas) {
        canvas.remove(rect);
    }
}
