import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Node object is going to have a parent, word and frequency
 **/

public class Node implements Comparable<Node> {

    // Max updates to the ArrayList before we sort it.
    private final int SORT_THRESHOLD = 5;

    ArrayList<Node> children = new ArrayList<>();
    HashMap<String, Integer> childrenIndex = new HashMap<>();

    // The number of updates to the ArrayList since we last sorted it.
    int updates = 0;

    // This node's word and it's frequency. Init count is always 1.
    String word;
    Integer count = 1;

    int level;

    public Node(String word, int level) {
        this.word = word;
        this.level = level;
    }

    private void sortArrayList() {
        updates = 0;
    }

    public void add(PentaGram p) {
        count += 1;
        String nextWord = p.getWord(level + 1);
        if (childrenIndex.containsKey(nextWord)) {
            children.get(childrenIndex.get(nextWord)).add(p);
        } else {
            children.add(new Node(nextWord, level + 1));
            childrenIndex.put(nextWord, children.size() - 1);
        }
        if (updates > SORT_THRESHOLD) {
            sortArrayList();
        }
    }

    @Override
    public int compareTo(Node n) {
        return n.count.compareTo(this.count);
    }

    // Use wisely
    @Override
    public String toString() {
        String result = word + ":" + count + "\n";
        for (Node node : children) {
            result += node.toString();
        }
        return result + "\n\n";
    }

}