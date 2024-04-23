import java.util.List;

public class BinarySearchTreeSorter {
    public static <T extends Comparable<T>> void sort(List<T> list) throws DuplicateElementException {
        // TODO: Posortuj listę używając klasy BinarySearchTree.
        BinarySearchTree<T> BST = new BinarySearchTree<>();
        for (T value : list)
            BST.add(value);

        list.clear();
        IntegerToListExec<T> exec = new IntegerToListExec<>(list);
        BST.inOrderWithExecutor(exec);
    }

    private static class IntegerToListExec<T> implements IExecutor<T, List<T>> {
        private List<T> list;

        public IntegerToListExec(List<T> list) {
            this.list = list;
        }

        @Override
        public void execute(T elem) {
            list.add(elem);
        }

        @Override
        public List<T> getResult() {
            return list;
        }
    }

}
