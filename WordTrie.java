import java.util.List;

/**
 * WordTrie.java
 * (c) Riwaz, Julian 2017
 */

public class WordTrie {

    CharNode root;

    public WordTrie() {
        this.root = new CharNode('\2');
    }

    public void add(String word) {
        CharNode n = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            n.add(c);
            n = n.getChild(c);
        }
        n.add(Params.END);
    }

    public List<String> completeWord(String word) {
        CharNode n = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            
        }
    }

}
