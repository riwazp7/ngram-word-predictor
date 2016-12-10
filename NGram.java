/**
 * Created by Riwaz on 12/10/16.
 */
public class NGram {
    protected int N;
    protected String[] words;

    public String getWord(int i) {
        if (i >= N) {
            throw new RuntimeException(N + "-gram has no " + i + "th element.");
        }
        return words[i];
    }
}
