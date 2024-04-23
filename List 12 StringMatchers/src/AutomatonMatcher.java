import java.util.ArrayList;
import java.util.List;

public class AutomatonMatcher implements IStringMatcher {
    @Override
    public List<Integer> validShifts(String textToSearch, String patternToFind) {
        // TODO: Zaimplementuj algorytm dopasowywania napisów oparty na automacie skończonym
        ArrayList<Integer> result = new ArrayList<>();
        char[] text = textToSearch.toCharArray();
        char[] pattern = patternToFind.toCharArray();
        int n = text.length;
        int m = pattern.length;

        int q = 0;
        for (int i = 0; i < n; i++) {
            q = computeTransitionFunction(pattern, q, text[i]);
            if (q == m)
                result.add(i - m + 1);
        }
        return result;
    }

    private int computeTransitionFunction(char[] pattern, int q, int t) {
        if (q < pattern.length && t == pattern[q])
            return q + 1;

        int i;
        for (int k = q - 1; k >= 0; k--) {
            if (pattern[k] == t) {
                for (i = 0; i < k; i++)
                    if (pattern[i] != pattern[q - k + i])
                        break;
                if (i == k)
                    return k + 1;
            }
        }
        return 0;
    }
}
