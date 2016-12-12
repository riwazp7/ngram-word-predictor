import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A Node object is going to have a parent, word and frequency
 **/

public class Node implements Comparable<Node> {

    // Max updates to the ArrayList before we sort it.
    protected final int SORT_THRESHOLD_FACTOR = 100;

    ArrayList<Node> children = new ArrayList<>();
    HashMap<String, Integer> childrenIndex = new HashMap<>();

    protected final int MAX_LEVEL;

    // This node's word frequency.
    protected Integer count = 0;
    protected String word;


    public Node(int MAX_LEVEL) {
        this.MAX_LEVEL = MAX_LEVEL;
    }

    // The number of updates to the ArrayList since we last sorted it.
    protected int updates = 0;

    private final int level = -1;

    protected void sortArrayList() {

        int noSorted = 1;
        int index;
        while (noSorted < children.size()) {
            Node temp = children.get(noSorted);
            for (index = noSorted; index > 0; index--) {
                if (temp.compareTo(children.get(index - 1)) > 0) {
                    children.set(index, children.get(index - 1));
                    childrenIndex.put(children.get(index - 1).getWord(), index);
                } else {
                    break;
                }
            }
            children.set(index, temp);
            childrenIndex.put(temp.getWord(), index);
            noSorted += 1;
        }

        /*
        Collections.sort(children);
        for (int i = 0; i < children.size(); i++) {
            childrenIndex.put(children.get(i).getWord(), i);
        }
        */
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
        if (updates > SORT_THRESHOLD_FACTOR) {
            sortArrayList();
        }
    }

    public List<String> predict(NGram n) {
        if (childrenIndex.containsKey(n.getWord(level + 1))) {
            return children.get(childrenIndex.get(n.getWord(level + 1))).predict(n);
        }
        return null;
    }

    public String getWord() {
        return this.word;
    }

    public String toString(String soFar) {
        String result = "";
        for (Node n : children) {
            result += (n.toString(""));
        }
        return result;
    }

    @Override
    public int compareTo(Node n) {
        return n.count.compareTo(this.count);
    }

}