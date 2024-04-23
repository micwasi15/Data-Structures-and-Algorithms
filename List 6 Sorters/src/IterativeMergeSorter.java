public class IterativeMergeSorter implements ISorter {
    private final IChecker checker;

    public IterativeMergeSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        int leftIndex;
        int splitIndex;
        int size;
        for (size = 2; size < 2 * values.length; size *= 2) {
            splitIndex = size / 2;
            for (int j = 0; j <= (values.length - 1) / size; j++) {
                leftIndex = j * size;
                if (leftIndex + size > values.length)
                    merge(leftIndex, splitIndex, values.length - leftIndex, values);
                else
                    merge(leftIndex, splitIndex, size, values);
            }
            checker.check(values);
        }
        checker.check(values);
        // Pamiętaj o wywołaniu checker.check(values); po kazdym wywołaniu zewnętrznej petli

    }

    private void merge(int beginIndex, int splitIdx, int size, int[] values) {
        if (splitIdx >= size)
            return;
        int[] copy = new int[size];
        int leftIdx = 0;
        int rightIdx = splitIdx;
        System.arraycopy(values, beginIndex, copy, 0, size);
        while (leftIdx < splitIdx && rightIdx < size) {
            if (copy[rightIdx] < copy[leftIdx]) {
                values[beginIndex] = copy[rightIdx];
                rightIdx++;
            } else {
                values[beginIndex] = copy[leftIdx];
                leftIdx++;
            }
            beginIndex++;
        }
        while (leftIdx < splitIdx) {
            values[beginIndex] = copy[leftIdx];
            leftIdx++;
            beginIndex++;
        }
        while (rightIdx < size) {
            values[beginIndex] = copy[rightIdx];
            rightIdx++;
            beginIndex++;
        }
    }
}