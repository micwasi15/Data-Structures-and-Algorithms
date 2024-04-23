public class SelectionSorter implements ISorter {
    private final IChecker checker;

    public SelectionSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        int smallest;
        for (int i = 0; i < values.length - 1; i++) {
            smallest = i;
            for (int j = i + 1; j < values.length; j++)
                if (values[smallest] > values[j])
                    smallest = j;
            if (i != smallest) {
                swap(values, smallest, i);
            }
            checker.check(values);
        }
        // Pamiętaj o wywołaniu checker.check(values); po kazdym wywołaniu zewnętrznej petli
    }

    private static void swap(int[] values, int first, int second) {
        int temp;
        temp = values[second];
        values[second] = values[first];
        values[first] = temp;
    }
}
