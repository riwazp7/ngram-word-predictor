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
        ProbabTree tree = new ProbabTree();
        tree.add(new PentaGram("the","big","brown","fox","jumped"));
        System.out.println(tree);
    }

}
