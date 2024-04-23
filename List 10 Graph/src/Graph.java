import java.util.*;

public class Graph<T> {
    private final HashMap<T, Integer> vertexIndex;
    private final int[][] neighborhoodMatrix;
    private final T[] vertices;

    @SuppressWarnings("unchecked")
    public Graph(List<Edge<T>> edges) {
        // TODO: Przekształcenie krawędzi na macierz sąsiedztwa, odwzorowanie wierzchołka na indeks, itp.
        vertexIndex = new HashMap<>();
        int index = 0;
        for (Edge<T> edge : edges) {
            if (!vertexIndex.containsKey(edge.getSource())) {
                vertexIndex.put(edge.getSource(), index);
                index++;
            }
            if (!vertexIndex.containsKey(edge.getDestination())) {
                vertexIndex.put(edge.getDestination(), index);
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
            x = vertexIndex.get(edge.getSource());
            y = vertexIndex.get(edge.getDestination());
            neighborhoodMatrix[x][y] = edge.getWeight();
        }
    }

    public String depthFirst(T startNode) throws NoSuchElementException {
        // TODO: Przejście przez graf metodą najpierw-wgłąb od podanego wierzchołka
        if (!vertexIndex.containsKey(startNode))
            throw new NoSuchElementException();
        StringBuffer stringBuffer = new StringBuffer();
        boolean[] visited = new boolean[vertexIndex.size()];
        //Arrays.fill(visited, false);
        int index = vertexIndex.get(startNode);

        depthFirstHelper(index, stringBuffer, visited);
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        return stringBuffer.toString();
    }

    private void depthFirstHelper(int index, StringBuffer stringBuffer, boolean[] visited) {
        stringBuffer.append(vertices[index]).append(", ");
        visited[index] = true;
        for (int i = 0; i < neighborhoodMatrix[index].length; i++)
            if (neighborhoodMatrix[index][i] > 0 && !visited[i])
                depthFirstHelper(i, stringBuffer, visited);
    }

    public String breadthFirst(T startNode) throws NoSuchElementException {
        // TODO: Przejście przez graf metodą najpierw-wszerz od podanego wierzchołka
        if (!vertexIndex.containsKey(startNode))
            throw new NoSuchElementException();
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Integer> queue = new ArrayList<>();
        boolean[] visited = new boolean[vertexIndex.size()]; //-1 - white, 0 - grey, 1 - black
        //Arrays.fill(visited, false);
        int index = vertexIndex.get(startNode);
        visited[index] = true;
        queue.add(index);

        while (!queue.isEmpty()) {
            index = queue.remove(0);
            stringBuilder.append(vertices[index]).append(", ");
            for (int i = 0; i < neighborhoodMatrix[index].length; i++)
                if (neighborhoodMatrix[index][i] > 0 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    public int connectedComponents() {
        // TODO: Liczba składowych spójnych grafu
        DisjointSetForest disjointSetForest = new DisjointSetForest(vertexIndex.size());
        for (int i = 0; i < neighborhoodMatrix.length; i++)
            for (int j = 0; j < neighborhoodMatrix.length; j++) {
                try {
                    if (neighborhoodMatrix[i][j] > 0)
                        disjointSetForest.union(i, j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return disjointSetForest.numberOfThrees();
    }
}
