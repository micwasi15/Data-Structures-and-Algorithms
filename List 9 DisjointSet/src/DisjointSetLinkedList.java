public class DisjointSetLinkedList implements IDisjointSetStructure {
    private final Node[] list;

    public DisjointSetLinkedList(int size) {
        list = new Node[size];
        for (int i = 0; i < size; i++) {
            list[i] = new Node(i, i);
        }
    }

    @Override
    public int findSet(int item) throws ItemOutOfRangeException {
        for (Node node : list) {
            if (node.value == item)
                return node.rep;
        }
        throw new ItemOutOfRangeException();
    }

    @Override
    public void union(int item1, int item2) throws ItemOutOfRangeException {
        int firstRepPos = findSet(item1);
        int secondRepPos = findSet(item2);
        Node newRep = list[firstRepPos];
        Node oldRep = list[secondRepPos];

        if (newRep.weight < oldRep.weight) {
            newRep = list[secondRepPos];
            oldRep = list[firstRepPos];
        }

        list[newRep.last].next = oldRep.rep;
        newRep.last = oldRep.last;
        newRep.weight += oldRep.weight;

        int next = oldRep.rep;
        Node temp;
        while (next != -1) {
            temp = list[next];
            temp.rep = newRep.rep;
            next = temp.next;
        }
    }

    private static class Node {
        int value;
        private int rep;
        private int next;
        private int last;
        private int weight;

        public Node(int value, int pos) {
            this.value = value;
            this.rep = pos;
            this.next = -1;
            this.last = pos;
            this.weight = 1;
        }
    }
}
