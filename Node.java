import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Node object is going to have a parent, word and frequency
 **/

public class Node {

    // Max updates to the ArrayList before we sort it.
    protected final int SORT_THRESHOLD = 5;

    ArrayList<Node> children = new ArrayList<>();
    HashMap<String, Integer> childrenIndex = new HashMap<>();

    protected final int MAX_LEVEL;

    public Node(int MAX_LEVEL) {
        this.MAX_LEVEL = MAX_LEVEL;
    }

    // The number of updates to the ArrayList since we last sorted it.
    protected int updates = 0;

    private final int level = -1;

    protected void sortArrayList() {
        updates = 0;
    }

    public void add(NGram p) {
        String nextWord = p.getWord(level + 1);
        if (childrenIndex.containsKey(nextWord)) {
            children.get(childrenIndex.get(nextWord)).add(p);
        } else {
            ChildNode child = new ChildNode(nextWord, level + 1, MAX_LEVEL);
            children.add(child);
            child.add(p);
            childrenIndex.put(nextWord, children.size() - 1);
        }
        updates++;
        if (updates > SORT_THRESHOLD) {
            sortArrayList();
        }
    }

    public String predict(NGram n) {

    }

    @Override
    public String toString() {
        String result =  "ROOT-->" + "(";
        for (int i = 0; i < children.size() - 1; i++) {
            Node node = children.get(i);
            result += (node.toString() + ", ");
        }
        return result + children.get(children.size() - 1) + ")";
    }

}