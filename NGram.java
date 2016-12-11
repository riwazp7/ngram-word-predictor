import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Riwaz on 12/10/16.
 */
public class NGram {
    public static final String BLANK = "ICED_TEA";
    public int N;
    private String[] words;

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

    public String[] getWords() {
        return words;
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

    public static NGram getQuadGram(String words) {
        words = words.toLowerCase();
        String filler = NGram.BLANK;

        words = words.replace(".", "");
        // store words in string array to easily determine number of words
        String[] splitWords = words.split(" ");

        NGram p = null;
        //determine if there are currently more than 4 words typed in
        int numWords = splitWords.length;
        if (numWords < 4) {
            if (numWords == 0) {
                p = null;
            } else if (numWords == 1) {
                p = new NGram(new String[]{filler, filler, filler, splitWords[0]});
            } else if (numWords == 2) {
                p = new NGram(new String[]{filler, filler, splitWords[0], splitWords[1]});
            } else if (numWords == 3) {
                p = new NGram(new String[]{filler, splitWords[0], splitWords[1], splitWords[2]});
            }
        } else {
            //just grab the last four words
            p = new NGram(new String[]{splitWords[numWords - 4], splitWords[numWords - 3],
                    splitWords[numWords - 2], splitWords[numWords - 1]});
        }

        return p;
    }

    public static List<NGram> getNGramsFromSentence(String sentence) {
        String filler = NGram.BLANK;
        String[] words = sentence.split(" ");
        NGram n;
        List<NGram> result = new ArrayList<>();
        for(int j = 0; j < words.length; j++){
            if( j == 0 ){
                n = new NGram(new String[]{filler, filler, filler, filler, words[j]});
            } else if ( j == 1 ) {
                n = new NGram(new String[] {filler,filler,filler,words[j-1],words[j]});
            } else if ( j == 2 ) {
                n = new NGram(new String[] {filler,filler,words[j-2],words[j-1],words[j]});
            } else if ( j == 3 ){
                n = new NGram(new String[] {filler,words[j-3],words[j-2],words[j-1],words[j]});
            } else {
                n = new NGram(new String[]{words[j - 4], words[j - 3], words[j - 2], words[j - 1], words[j]});
            }
            result.add(n);
        }
        return result;
    }

    public static NGram getOneSmallerNgram(NGram n) {
        if (n.N == 1)return null;
        else return new NGram(Arrays.copyOfRange(n.getWords(), 1, n.getWords().length));
    }

    @Override
    public String toString() {
        return Arrays.toString(words);
    }
}
