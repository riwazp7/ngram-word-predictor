/**
 * Created by Riwaz on 12/9/16.
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ProbabTree {

    Node root;

    // Choose a max_level int
    public ProbabTree() {
        root = new Node(30);
    }

    public void add(PentaGram p) {
        root.add(p);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        // ******** TEST ***********
        ProbabTree tree = new ProbabTree();

        String[] files = new String[]{"test.txt"};

        WordPredictor grams = new WordPredictor(files);
        ArrayList<LinkedList<PentaGram>> pentaGrams = grams.pentaGrams;

        for(LinkedList<PentaGram> penta : pentaGrams){
            for(PentaGram p : penta){
                tree.add(p);
            }
        }

        /**
        tree.add(new PentaGram(new String[] {"the","big","brown","fox","jumped"}));
        tree.add(new PentaGram(new String[] {"the","big","brown","fox","jumped"}));
        tree.add(new PentaGram(new String[] {"the","big","man","is","nice"}));
        tree.add(new PentaGram(new String[]{"a","big","man","is","nice"}));
         **/
        System.out.println(tree);
    }

}
