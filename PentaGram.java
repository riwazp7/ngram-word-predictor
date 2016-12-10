/**
 * Created by Riwaz on 12/9/16.
 */
public class PentaGram extends NGram {

    public PentaGram(String[] words) {
        this.N = 5;
        if (words.length != N) {
            throw new RuntimeException("Pentagram can only be initialized with a 5 long Array");
        }
        this.words = words;
    }

}
