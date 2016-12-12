/** ProbTree.java
 *  Maintains trees of Nodes that store N-gram, and generates predictions.
 *  As of now, a tree for PentaGrams, QuadGrams, Trigrams, and BiGrams are stored.
 *  (Unigrams are handled in WordPredictor.java)
 *  When asked for a prediction, scans the biggest N-gram tree and switches down until NO_OF_SUGGESTIONS
 *  number of words are found.
 *  (c) Julian Vera, Riwaz Poudyal 2016
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProbTree {

    public static final int NO_OF_SUGGESTIONS = Params.NO_OF_SUGGESTIONS;

    // Max N-gram to store is pentagram
    private final int MAX_LEVEL = 4;

    //Roots of the N-gram trees.
    private Node[] roots;

    private Filter filter;

    public ProbTree(Filter filter) {
        this.filter = filter;
        roots = new Node[MAX_LEVEL];
        for (int i = 0; i < MAX_LEVEL; i++) {
            roots[i] = new Node(i + 1);
        }
    }

    public void add(NGram n) {
        for (int i = roots.length - 1; i >= 0; i--) {
            roots[i].add(n);
            n = NGram.getOneSmallerNgram(n);
        }
    }

    // Add method that doesn't affect sorting. For initial training only.
    public void initialAdd(NGram n) {
        for (int i = roots.length - 1; i >= 0; i--) {
            roots[i].initialAdd(n);
            n = NGram.getOneSmallerNgram(n);
        }
    }

    // recursively sort all nodes of the tree after initial training.
    public void initialSort() {
        for (int i = roots.length - 1; i >= 0; i--) {
            roots[i].initialSort();
        }
    }

    // Get prediction for query n-gram
    public List<String> predictNextWords(NGram n) {
        if (n.N > MAX_LEVEL) {
            throw new RuntimeException("Cannot predict for " + n + "when MAX_LEVEL is " + MAX_LEVEL);
        }
        List<String> result = new ArrayList<>();
        // Switch down until the required number of predictions are found.
        while (n != null && n.N >= 1) {
            List<String> prediction = roots[n.N - 1].predict(n);
            if (prediction != null) {
                for (String word : prediction) {
                    if (!word.isEmpty() && !result.contains(word) && !filter.contains(word)) {
                        result.add(word);
                        if (result.size() >= NO_OF_SUGGESTIONS) return result;
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

    // ************** TEST ***********************
    public static void main(String[] args) throws FileNotFoundException {
        ProbTree tree = new ProbTree(new Filter(Params.WORDS_TO_FILTER));

        tree.add(new NGram(new String[] {"the","big","brown","fox","jumped"}));
        tree.add(new NGram(new String[] {"the","big","man","is","nice"}));
        tree.add(new NGram(new String[] {"a","big","man","is","nice"}));
        System.out.println(tree);
    }

}
