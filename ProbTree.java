import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riwaz on 12/9/16.
 */
public class ProbTree {

    public static final int NO_OF_SUGGESTION = Params.NO_OF_SUGGESTIONS;

    private final int MAX_LEVEL = 4;

    private Node[] roots;
    private Filter filter;

    public ProbTree() {
        roots = new Node[MAX_LEVEL];
        for (int i = 0; i < MAX_LEVEL; i++) {
            roots[i] = new Node(i + 1);
        }
    }

    public ProbTree(Filter filter) {
        this();
        this.filter = filter;
    }

    public void add(NGram n) {
        for (int i = roots.length - 1; i >= 0; i--) {
            roots[i].add(n);
            n = NGram.getOneSmallerNgram(n);
        }
    }

    public void initialAdd(NGram n) {
        for (int i = roots.length - 1; i >= 0; i--) {
            roots[i].initialAdd(n);
            n = NGram.getOneSmallerNgram(n);
        }
    }

    public void initialSort() {
        for (int i = roots.length - 1; i >= 0; i--) {
            roots[i].initialSort();
        }
    }

    public List<String> predictNextWords(NGram n) {
        if (n.N > MAX_LEVEL) {
            throw new RuntimeException("Cannot predict for " + n + "when MAX_LEVEL is " + MAX_LEVEL);
        }
        List<String> result = new ArrayList<>();
        while (n != null && n.N >= 1) {
            List<String> prediction = roots[n.N - 1].predict(n);
            if (prediction != null) {
                for (String word : prediction) {
                    if (!word.isEmpty() && !result.contains(word) && !filter.contains(word)) {
                        result.add(word);
                        if (result.size() >= NO_OF_SUGGESTION) return result;
                    }
                }
            }
            n = NGram.getOneSmallerNgram(n);
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < MAX_LEVEL; i++) {
            result += "*** " + (i+2) + "-grams ***\n";
            result += (roots[i].toString("") + "\n");
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ProbTree tree = new ProbTree();

        tree.add(new NGram(new String[] {"the","big","brown","fox","jumped"}));
        tree.add(new NGram(new String[] {"the","big","man","is","nice"}));
        tree.add(new NGram(new String[] {"a","big","man","is","nice"}));
        System.out.println(tree);
    }

}
