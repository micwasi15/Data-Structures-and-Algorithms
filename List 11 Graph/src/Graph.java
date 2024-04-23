import java.util.*;

public class Graph<T> {
    private final HashMap<T, Integer> vertexIndex;
    private final int[][] neighborhoodMatrix;
    private final T[] vertices;

    @SuppressWarnings("unchecked")
    public Graph(List<Edge<T>> edges) {
        // TODO: Konstruktor
        vertexIndex = new HashMap<>();
        int index = 0;
        for (Edge<T> edge : edges) {
            if (!vertexIndex.containsKey(edge.getNode1())) {
                vertexIndex.put(edge.getNode1(), index);
                index++;
            }
            if (!vertexIndex.containsKey(edge.getNode2())) {
                vertexIndex.put(edge.getNode2(), index);
                index++;
            }
        }

        int n = vertexIndex.size();
        neighborhoodMatrix = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == j)
                    neighborhoodMatrix[i][j] = 0;
                else
                    neighborhoodMatrix[i][j] = -1;
            }

        vertices = (T[]) new Object[n];
        for (T key : vertexIndex.keySet())
            vertices[vertexIndex.get(key)] = key;

        int x, y;
        for (Edge<T> edge : edges) {
            x = vertexIndex.get(edge.getNode1());
            y = vertexIndex.get(edge.getNode2());
            neighborhoodMatrix[x][y] = edge.getDistance();
            neighborhoodMatrix[y][x] = edge.getDistance();
        }
    }

    public Map<T, Integer> calculateShortestPaths(T startNode) throws NoSuchElementException {
        // TODO: Wylicz najkrótsze ścieżki do każdego wierzchołka w grafie (Dijkstra)
        if (!vertexIndex.containsKey(startNode))
            throw new NoSuchElementException();
        Map<T, Integer> map = new HashMap<>();
        Comparator<T> comp = Comparator.comparingInt(map::get);
        PriorityQueue<T> queue = new PriorityQueue<>(comp);
        boolean[] settled = new boolean[vertexIndex.size()];
        map.put(startNode, 0);
        queue.add(startNode);


        T key;
        while (!queue.isEmpty()) {
            key = queue.poll();
            updateNeighbours(key, map, queue, settled);
        }

        map.remove(startNode);
        return map;
    }

    private void updateNeighbours(T node, Map<T, Integer> map, PriorityQueue<T> queue, boolean[] settled) {
        int index = vertexIndex.get(node);
        int distance = map.get(node);
        T key;
        settled[index] = true;
        for (int i = 0; i < neighborhoodMatrix[index].length; i++) {
            if (neighborhoodMatrix[index][i] > 0 && !settled[i]) {
                key = vertices[i];
                if (map.containsKey(key))
                    map.put(key, Math.min(map.get(key), distance + neighborhoodMatrix[index][i]));
                else
                    map.put(key, distance + neighborhoodMatrix[index][i]);
                if (!queue.contains(key))
                    queue.add(key);
            }
        }
    }

    public Integer calculateShortestPath(T startNode, T endNode) throws NoSuchElementException {
        // TODO: Wylicz najkrótszą ścieżkę pomiędzy wierzchołkami w grafie
        if (!vertexIndex.containsKey(endNode) || !vertexIndex.containsKey(startNode))
            throw new NoSuchElementException();
        Map<T, Integer> map = new HashMap<>();
        Comparator<T> comp = Comparator.comparingInt(map::get);
        PriorityQueue<T> queue = new PriorityQueue<>(comp);
        boolean[] settled = new boolean[vertexIndex.size()];
        map.put(startNode, 0);
        queue.add(startNode);

        T key;
        while (!queue.isEmpty()) {
            key = queue.poll();
            if (key.equals(endNode))
                break;
            updateNeighbours(key, map, queue, settled);
        }

        return map.get(endNode);
    }
}
