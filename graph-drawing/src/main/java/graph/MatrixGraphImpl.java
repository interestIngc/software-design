package graph;

import drawing.DrawingApi;

import java.util.ArrayList;
import java.util.List;

public class MatrixGraphImpl extends Graph {
    private final List<List<Boolean>> matrix;
    private final List<Edge> edges;

    public MatrixGraphImpl(DrawingApi drawingApi, List<List<Boolean>> matrix) {
        super(drawingApi);
        this.matrix = matrix;

        edges = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            List<Boolean> neighbours = matrix.get(i);
            for (int j = 0; j < neighbours.size(); j++) {
                if (i <= j && neighbours.get(j)) {
                    edges.add(new Edge(i, j));
                }
            }
        }
    }

    @Override
    public int verticesCount() {
        return matrix.size();
    }

    @Override
    public List<Edge> edges() {
        return edges;
    }
}
