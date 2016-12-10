/**
 * Created by julianvera on 12/10/16.
 */
public class QuadGram extends NGram {

    public QuadGram(String[] words) {
        this.N = 4;
        if (words.length != N) {
            throw new RuntimeException("Pentagram can only be initialized with a 5 long Array");
        }
        this.words = words;
    }
}
