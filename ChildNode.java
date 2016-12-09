/**
 * A Node object is going to have a parent, word and frequency
 **/

public class ChildNode extends Node implements Comparable<ChildNode> {


    // This node's word and it's frequency. Init count is always 1.
    String word;
    Integer count = 1;

    int level;

    public ChildNode(String word, int level) {
        this.word = word;
        this.level = level;
    }

    public void add(PentaGram p) {
        count += 1;
        if (level < 4) {
            super.add(p);
        }
    }

    @Override
    public int compareTo(ChildNode n) {
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
