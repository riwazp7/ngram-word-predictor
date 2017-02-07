import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riwaz on 2/7/17.
 */
public class CharNode {

    protected Character c;
    protected List<CharNode> childList;

    public CharNode(Character c) {
        this.c = c;
        this.childList = new ArrayList<>();
    }

    public void add(Character c) {

    }

    public CharNode getChild(Character c) {
        return null;
    }
}
