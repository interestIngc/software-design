package graph;

import drawing.DrawingApi;

import java.util.ArrayList;
import java.util.List;

public class EdgesListGraphImpl extends Graph {
    private final List<List<Integer>> graph;
    private final List<Edge> edges;

    public EdgesListGraphImpl(DrawingApi drawingApi, List<List<Integer>> graph) {
        super(drawingApi);
        this.graph = graph;

        edges = new ArrayList<>();
        for (int i = 0; i < graph.size(); i++) {
            for (int j : graph.get(i)) {
                if (j >= i) {
                    edges.add(new Edge(i, j));
                }
            }
        }
    }

    @Override
    public int verticesCount() {
        return graph.size();
    }

    @Override
    public List<Edge> edges() {
        return edges;
    }
}
