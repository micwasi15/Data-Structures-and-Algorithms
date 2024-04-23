public class InvertedSelectionSorter implements ISorter {
    private final IChecker checker;

    public InvertedSelectionSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        int biggest;
        for (int i = values.length - 1; i > 0; i--) {
            biggest = i;
            for (int j = i - 1; j >= 0; j--)
                if (values[biggest] < values[j])
                    biggest = j;
            if (i != biggest) {
                swap(values, biggest, i);
            }
            checker.check(values);
        }
        // Zaimplementuj algorytm SelectionSort "w drugą stronę" (czyli przechodząc od drugiej strony tablicy)
        // Pamiętaj o wywołaniu checker.check(values); po kazdym wywołaniu zewnętrznej petli
        checker.check(values);
    }

    private static void swap(int[] values, int first, int second) {
        int temp;
        temp = values[second];
        values[second] = values[first];
        values[first] = temp;
    }
}
