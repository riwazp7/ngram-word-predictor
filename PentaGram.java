/**
 * Created by Riwaz on 12/9/16.
 */
public class PentaGram {
    private final String[] words;

    public PentaGram(String s1, String s2, String s3, String s4, String s5) {
       this.words = new String[] {s1, s2, s3, s4, s5};
    }

    public String getWord(int i) {
        return words[i];
    }

}
