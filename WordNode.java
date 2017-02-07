/** WordNode.java
 *  Suoerclass of all nodes. This class itself is the node at the highest level in the tree.
 *  Maintains word, its count at this branch, and its children.
 *  Children are sorted by their count so that predicting is fast. A hash map is maintained with the index of
 *  words in the children list so that adding new words to the array is fast.
 *  The children list is only sorted after a number of updates. This number is high for higher level trees because
 *  we don't expect significant changes when a few elements are added because of their size.
 *  This number is scaled down by a factor of ~(level)^2 so that lower level nodes are sorted very frequently.
 *  We use insertion sort to get ~O(n) run time for sort.
 *  (c) Riwaz Poudyal, Julian Vera 2016
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNode implements Comparable<WordNode> {

    // Max updates to the ArrayList before we sort it.
    protected final int SORT_THRESHOLD_FACTOR = Params.SORT_THRESHOLD_FACTOR;

    ArrayList<WordNode> children = new ArrayList<>();
    HashMap<String, Integer> childrenIndex = new HashMap<>();

    // Max level of the tree this node belongs to
    protected final int MAX_LEVEL;

    // This node's word frequency.
    protected Integer count = 0;
    protected String word;

    // This node's level. -1 for top level node.
    protected int level = -1;

    public WordNode(int MAX_LEVEL) {
        this.MAX_LEVEL = MAX_LEVEL;
    }

    public void add(NGram p) {
        String nextWord = p.getWord(level + 1);
        if (childrenIndex.containsKey(nextWord)) {
            children.get(childrenIndex.get(nextWord)).add(p);
        } else {
            ChildWordNode child = new ChildWordNode(nextWord, level + 1, MAX_LEVEL);
            children.add(child);
            child.add(p);
            childrenIndex.put(nextWord, children.size() - 1);
        }
    }

    // Add method that doesn't force sorting. For initial training as we don't have to do any prediction
    // when the training is occuring.
    public void initialAdd(NGram p) {
        String nextWord = p.getWord(level + 1);
        if (childrenIndex.containsKey(nextWord)) {
            children.get(childrenIndex.get(nextWord)).add(p);
        } else {
            ChildWordNode child = new ChildWordNode(nextWord, level + 1, MAX_LEVEL);
            children.add(child);
            child.initialAdd(p);
            childrenIndex.put(nextWord, children.size() - 1);
        }
    }

    // recursively sort the children after initial training.
    public void initialSort() {
        for (WordNode n : children) {
            n.initialSort();
        }
    }

    public List<String> predict(NGram n) {
        if (childrenIndex.containsKey(n.getWord(level + 1))) {
            return children.get(childrenIndex.get(n.getWord(level + 1))).predict(n);
        }
        // No entry for this n-gram, so will have to try n-1 gram instead.
        return null;
    }

    public String getWord() {
        return this.word;
    }

    // Recursively create a String repr of this node and its children.
    public String toString(String soFar) {
        String result = "";
        for (WordNode n : children) {
            result += (n.toString(""));
        }
        return result;
    }

    @Override
    public int compareTo(WordNode n) {
        return n.count.compareTo(this.count);
    }

}