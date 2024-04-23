public class RadixSorter implements ISorter {
    private final IChecker checker;

    public RadixSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        int pow = 1;
        for (int i : values) {
            while (pow <= i)
                pow *= 10;
        }
        for (int i = 1; i < pow; i *= 10) {
            countingSort(values, i);
            checker.check(values);
        }
        // Pamiętaj o wywołaniu checker.check(values); po kazdym wywołaniu zewnętrznej petli
    }

    private void countingSort(int[] values, int pow) {
        int numberOfDigits = 10;
        int n = values.length;
        int[] pos = new int[numberOfDigits];
        int[] result = new int[n];
        int[] digits = new int[n];
        int i;
        int j;

        for (int k = 0; k < n; k++)
            digits[k] = values[k] / pow % 10;
        for (i = 0; i < numberOfDigits; i++)
            pos[i] = 0;
        for (j = 0; j < n; j++)
            pos[digits[j]]++;
        pos[0]--;
        for (i = 1; i < numberOfDigits; i++)
            pos[i] += pos[i - 1];
        for (j = n - 1; j >= 0; j--) {
            result[pos[digits[j]]] = values[j];
            pos[digits[j]]--;
        }
        for (j = 0; j < n; j++)
            values[j] = result[j];
    }
}
