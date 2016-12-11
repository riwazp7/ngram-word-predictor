import java.util.ArrayList;
import java.util.List;

/**
 * A ChildNode object
 **/

public class ChildNode extends Node implements Comparable<ChildNode> {


    // This node's word frequency.
    private Integer count = 0;

    int level;

    public ChildNode(String word, int level, int MAX_LEVEL) {
        super(MAX_LEVEL);
        this.word = word;
        this.level = level;
    }

    @Override
    public void add(NGram p) {
        count += 1;
        if (level < MAX_LEVEL) {
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
    }

    @Override
    public int compareTo(ChildNode n) {
        return n.count.compareTo(this.count);
    }

    @Override
    public String toString() {
        String result = word + ":" + count + "-->" + "(";
        if (level == 4) return result + ")";
        for (int i = 0; i < children.size() - 1; i++) {
            Node node = children.get(i);
            result += (node.toString() + ", ");
        }
        return result + children.get(children.size() - 1) + ")";
    }

    @Override
    public List<String> predict(NGram n) {
        if (this.level + 1 == MAX_LEVEL) {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < ProbTree.NO_OF_SUGGESTION && i < children.size(); i++) {
                result.add(children.get(i).getWord());
            }
            return result;
        } else if (childrenIndex.containsKey(n.getWord(level + 1))) {
            return children.get(childrenIndex.get(n.getWord(level + 1))).predict(n);
        }
        return null;
    }

}
