public class InsertionSorter implements ISorter {
    private final IChecker checker;

    public InsertionSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        int value;
        int j;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            for (j = i; j > 0 && values[j - 1] > value; j--) {
                values[j] = values[j-1];
            }
            values[j] = value;
            checker.check(values);
        }
        // Pamiętaj o wywołaniu checker.check(values); po kazdym wywołaniu zewnętrznej petli
    }
}
