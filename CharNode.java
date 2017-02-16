import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Riwaz on 2/7/17.
 */
public class CharNode {

    protected Character c;
    protected List<CharNode> childList;
    protected HashMap<Character, Integer> indexMap = new HashMap<>();
    protected int count = 0;

    public CharNode(Character c) {
        this.c = c;
        this.childList = new ArrayList<>();
    }

    public void add(Character c) {
        if (indexMap.containsKey(c)) {
            childList.get(indexMap.get(c)).incrementCount();
        } else {
            CharNode child = new CharNode(c);
            childList.add(child);
            indexMap.put(c, childList.size()-1);
            child.incrementCount();
        }
    }

    public void incrementCount() {
        count +=1;
    }

    public CharNode getChild(Character c) {
        return childList.get(indexMap.get(c));
    }
}
