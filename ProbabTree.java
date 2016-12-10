/**
 * Created by Riwaz on 12/9/16.
 */
public class ProbabTree {

    Node root;

    public ProbabTree() {
        root = new Node();
    }

    public void add(PentaGram p) {
        root.add(p);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public static void main(String[] args) {
        // ******** TEST ***********
        ProbabTree tree = new ProbabTree();
        tree.add(new PentaGram(new String[] {"the","big","brown","fox","jumped"}));
        tree.add(new PentaGram(new String[] {"the","big","brown","fox","jumped"}));
        tree.add(new PentaGram(new String[] {"the","big","man","is","nice"}));
        tree.add(new PentaGram(new String[]{"a","big","man","is","nice"}));
        System.out.println(tree);
    }

}
