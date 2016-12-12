/** ChildNode.java
 *  Represents all nodes not at the top level.
 *  (c) Riwaz Poudyal, Julian Vera 2016
 */

import java.util.ArrayList;
import java.util.List;

public class ChildNode extends Node {


    public ChildNode(String word, int level, int MAX_LEVEL) {
        super(MAX_LEVEL);
        this.word = word;
        this.level = level;
    }

    @Override
    public void add(NGram p) {
        if (p.N != MAX_LEVEL + 1) {
            throw new RuntimeException("Cannot add " + p.N + "-gram to a tree with MAX_LEVEL " + MAX_LEVEL);
        }
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
            if (updates > SORT_THRESHOLD_FACTOR / (level + 2) / (level + 2)) {
                sortArrayList();
            }
        }
    }

    @Override
    public void initialAdd(NGram p) {
        if (p.N != MAX_LEVEL + 1) {
            throw new RuntimeException("Cannot add " + p.N + "-gram to a tree with MAX_LEVEL " + MAX_LEVEL);
        }
        count += 1;
        if (level < MAX_LEVEL) {
            String nextWord = p.getWord(level + 1);
            if (childrenIndex.containsKey(nextWord)) {
                children.get(childrenIndex.get(nextWord)).add(p);
            } else {
                ChildNode child = new ChildNode(nextWord, level + 1, MAX_LEVEL);
                children.add(child);
                child.initialAdd(p);
                childrenIndex.put(nextWord, children.size() - 1);
            }
        }
    }

    @Override
    public void initialSort() {
        sortArrayList();
        for (Node n : children) {
            n.initialSort();
        }
    }

    public String toString(String soFar) {
        soFar = soFar + word + ": " + count + " ";
        if (children.isEmpty()) return soFar + "\n";
        String result = "";
        for (Node n : children) {
            result += (n.toString(soFar) + "\n");
        }
        return result.substring(0,result.length() - 1);
    }

    @Override
    public List<String> predict(NGram n) {
        if (this.level + 1 == MAX_LEVEL) {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < ProbTree.NO_OF_SUGGESTIONS && i < children.size(); i++) {
                result.add(children.get(i).getWord());
            }
            return result;
        } else if (childrenIndex.containsKey(n.getWord(level + 1))) {
            return children.get(childrenIndex.get(n.getWord(level + 1))).predict(n);
        }
        return null;
    }

}
