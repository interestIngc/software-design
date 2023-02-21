package drawing;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class AwtDrawingApi extends Frame implements DrawingApi {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final int PADDING = 30;

    private final List<Circle> circles = new ArrayList<>();
    private final List<Line> lines = new ArrayList<>();

    public AwtDrawingApi() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        setSize(WIDTH, HEIGHT);
    }

    @Override
    public long getDrawingAreaWidth() {
        return WIDTH - PADDING;
    }

    @Override
    public long getDrawingAreaHeight() {
        return HEIGHT - PADDING;
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
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics = (Graphics2D) g;
        graphics.setPaint(Color.BLACK);

        for (Circle circle : circles) {
            double centerX = circle.getCenterX();
            double centerY = circle.getCenterY();
            double radius = circle.getRadius();
            graphics.fill(
                    new Ellipse2D.Double(
                            PADDING + centerX - radius,
                            PADDING + centerY - radius,
                            2 * radius,
                            2 * radius
                    )
            );
        }

        for (Line line : lines) {
            graphics.draw(
                    new Line2D.Double(
                            PADDING + line.fromX,
                            PADDING + line.fromY,
                            PADDING + line.toX,
                            PADDING + line.toY
                    )
            );
        }
    }

    @Override
    public void display() {
        setVisible(true);
    }
}
