package drawing;

public interface DrawingApi {
    long getDrawingAreaWidth();

    long getDrawingAreaHeight();

    void drawCircle(double centerX, double centerY, double radius);

    void drawLine(double fromX, double fromY, double toX, double toY);

    void display();
}
