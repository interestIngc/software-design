package drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class JavaFxDrawingApi extends Application implements DrawingApi {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final List<Circle> circles = new ArrayList<>();
    private static final List<Line> lines = new ArrayList<>();

    @Override
    public long getDrawingAreaWidth() {
        return WIDTH;
    }

    @Override
    public long getDrawingAreaHeight() {
        return HEIGHT;
    }

    @Override
    public void drawCircle(double centerX, double centerY, double radius) {
        circles.add(new Circle(centerX, centerY, radius));
    }

    @Override
    public void drawLine(double fromX, double fromY, double toX, double toY) {
        lines.add(new Line(fromX, fromY, toX, toY));
    }

    @Override
    public void display() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Graph");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.BLACK);
        context.setStroke(Color.BLACK);

        for (Circle circle : circles) {
            double centerX = circle.getCenterX();
            double centerY = circle.getCenterY();
            double radius = circle.getRadius();
            context.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        }

        for (Line line : lines) {
            context.strokeLine(line.getFromX(), line.getFromY(), line.getToX(), line.getToY());
        }

        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
