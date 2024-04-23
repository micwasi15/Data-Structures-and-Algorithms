
public class DisjointSetForest implements IDisjointSetStructure {
    private final Node[] list;

    public DisjointSetForest(int size) {
        list = new Node[size];
        for (int i = 0; i < size; i++)
            list[i] = new Node(i);
    }

    @Override
    public int findSet(int item) throws ItemOutOfRangeException {
        if (item >= list.length || item < 0)
            throw new ItemOutOfRangeException();
        Node node = list[item];
        while (node != list[node.parent])
            node = list[node.parent];
        return node.parent;
    }

    @Override
    public void union(int item1, int item2) throws ItemOutOfRangeException {
        Node firstRep = list[findSet(item1)];
        Node secondRep = list[findSet(item2)];

        if (!firstRep.equals(secondRep)) {
            if (firstRep.rank > secondRep.rank) {
                secondRep.parent = firstRep.parent;
            } else {
                firstRep.parent = secondRep.parent;
                if (firstRep.rank == secondRep.rank)
                    secondRep.rank++;
            }
        }
    }

    public int numberOfThrees() {
        int result = 0;
        for (Node node : list)
            if (list[node.parent] == node)
                result++;
        return result;
    }

    private static class Node {
        private int parent;
        private int rank;

        public Node(int pos) {
            parent = pos;
            rank = 0;
        }
    }
}
