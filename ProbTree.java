import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riwaz on 12/9/16.
 */
public class ProbTree {

    private final int MAX_LEVEL = 4;
    public static final int NO_OF_SUGGESTION = 4;

    Node root;

    public ProbTree() {
        root = new Node(MAX_LEVEL);
    }

    public void add(PentaGram p) {
        root.add(p);
    }

    public List<String> predictNextWords(NGram n) {
        if (n.N > MAX_LEVEL) {
            throw new RuntimeException("Cannot predict for " + n + "when MAX_LEVEL is " + MAX_LEVEL);
        }
        List<String> result = new ArrayList<>();
        while (true) {
            List<String> prediction = root.predict(n);
            if (prediction != null) {
                for (String word : prediction) {
                    result.add(word);
                    if (result.size() > NO_OF_SUGGESTION) return result;
                }
            }
        }
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public static void main(String[] args) {
        ProbTree tree = new ProbTree();
        tree.add(new PentaGram(new String[] {"the","big","brown","fox","jumped"}));
        tree.add(new PentaGram(new String[] {"the","big","brown","fox","jumped"}));
        tree.add(new PentaGram(new String[] {"the","big","man","is","nice"}));
        tree.add(new PentaGram(new String[]{"a","big","man","is","nice"}));
        System.out.println(tree);
    }

}
