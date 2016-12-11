import java.util.Arrays;

/**
 * Created by Riwaz on 12/10/16.
 */
public class NGram {
    public static final String BLANK = "\1";
    protected int N;
    protected String[] words;

    public NGram(String[] words) {
        this.words = words;
        this.N = words.length;
    }

    public String getWord(int i) {
        if (i >= N) {
            throw new RuntimeException(N + "-gram has no " + i + "th element.");
        }
        return words[i];
    }

    // Creates a new copy of Ngram n and changes the first non-blank String to blank.
    public static NGram insertBlank(NGram n) {
        String[] words = new String[n.N];
        boolean blankInserted = false;
        for(int i = 0; i < words.length; i++) {
            if (!blankInserted && !n.words[i].equals(BLANK)) {
                words[i] = BLANK;
                blankInserted = true;
            } else {
                words[i] = n.words[i];
            }
        }
        return new NGram(words);
    }

    @Override
    public String toString() {
        return Arrays.toString(words);
    }
}
