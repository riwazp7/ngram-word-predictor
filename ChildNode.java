/**
 * A Node object is going to have a parent, word and frequency
 **/

public class ChildNode extends Node implements Comparable<ChildNode> {


    // This node's word and it's frequency.
    String word;
    Integer count = 0;

    int level;

    public ChildNode(String word, int level) {
        this.word = word;
        this.level = level;
    }

    public void add(PentaGram p) {
        count += 1;
        if (level < 4) {
            String nextWord = p.getWord(level + 1);
            if (childrenIndex.containsKey(nextWord)) {
                children.get(childrenIndex.get(nextWord)).add(p);
            } else {
                ChildNode child = new ChildNode(nextWord, level + 1);
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

    // Use wisely
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

}
