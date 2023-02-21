import drawing.AwtDrawingApi;
import drawing.DrawingApi;
import drawing.JavaFxDrawingApi;
import graph.EdgesListGraphImpl;
import graph.Graph;
import graph.MatrixGraphImpl;

import java.util.List;

public class Main {
    private static final List<List<Integer>> EDGES_LIST = List.of(
            List.of(1, 2, 3),
            List.of(0, 2, 3),
            List.of(0, 1),
            List.of(0, 1)
    );
    private static final List<List<Boolean>> ADJACENCY_MATRIX = List.of(
            List.of(false, true, true, true),
            List.of(true, false, true, true),
            List.of(true, true, false, false),
            List.of(true, true, false, false)
    );

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid number of args, found " + args.length + ", expected 2");
        }

        String graphType = args[0];
        String drawingApiType = args[1];

        DrawingApi drawingApi;
        Graph graph;
        switch (drawingApiType) {
            case "awt": {
                drawingApi = new AwtDrawingApi();
                break;
            }
            case "javafx": {
                drawingApi = new JavaFxDrawingApi();
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid drawing api type");
        }
        switch (graphType) {
            case "edgesList": {
                graph = new EdgesListGraphImpl(drawingApi, EDGES_LIST);
                break;
            }
            case "adjacencyMatrix": {
                graph = new MatrixGraphImpl(drawingApi, ADJACENCY_MATRIX);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid graph type");
        }

        graph.drawGraph();
    }
}
