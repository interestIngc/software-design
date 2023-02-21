package graph;

import drawing.DrawingApi;

import java.util.ArrayList;
import java.util.List;

public abstract class Graph {
    private static final int VERTEX_RADIUS = 5;
    final DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract int verticesCount();

    public abstract List<Edge> edges();

    public void drawGraph() {
        double screenWidth = drawingApi.getDrawingAreaWidth();
        double screenHeight = drawingApi.getDrawingAreaHeight();

        double centerX = screenWidth / 2;
        double centerY = screenHeight / 2;
        double radius = Math.min(screenWidth, screenHeight) / 2 - VERTEX_RADIUS;

        int n = verticesCount();
        double alpha = (2 * Math.PI) / n;

        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double x = centerX + radius * Math.cos(i * alpha);
            double y = centerY + radius * Math.sin(i * alpha);
            drawingApi.drawCircle(x, y, VERTEX_RADIUS);
            vertices.add(new Vertex(x, y));
        }

        for (Edge edge : edges()) {
            Vertex from = vertices.get(edge.from);
            Vertex to = vertices.get(edge.to);
            drawingApi.drawLine(from.x, from.y, to.x, to.y);
        }

        drawingApi.display();
    }

    protected static class Vertex {
        final double x;
        final double y;

        public Vertex(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    protected static class Edge {
        final int from;
        final int to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
