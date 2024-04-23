import java.util.ArrayList;
import java.util.List;

public class KMPMatcher implements IStringMatcher {
    @Override
    public List<Integer> validShifts(String textToSearch, String patternToFind) {
        // TODO: Zaimplementuj algorytm dopasowywania napisów Knutha-Morrisa-Pratta
        ArrayList<Integer> result = new ArrayList<>();
        char[] text = textToSearch.toCharArray();
        char[] pattern = patternToFind.toCharArray();
        int n = text.length;
        int m = pattern.length;
        int q = 0;
        int[] pf = computePrefixFunction(pattern);

        for (int i = 1; i <= n; i++) {
            while (q > 0 && pattern[q] != text[i - 1])
                q = pf[q];
            if (pattern[q] == text[i - 1])
                q++;
            if (q == m) {
                result.add(i - m);
                q = pf[q];
            }
        }

        return result;
    }

    private int[] computePrefixFunction(char[] pattern) {
        int m = pattern.length;
        int[] result = new int[m + 1];
        result[1] = 0;
        int k = 0;

        for (int q = 2; q <= m; q++) {
            while (k > 0 && pattern[k] != pattern[q - 1])
                k = result[k];
            if (pattern[k] == pattern[q - 1])
                k++;
            result[q] = k;
        }
        return result;
    }
}
