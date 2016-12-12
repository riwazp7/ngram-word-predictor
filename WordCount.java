/**
 * Created by Riwaz on 12/12/16.
 */
public class WordCount implements Comparable<WordCount> {
    private String word;
    private Integer count;

    public WordCount(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    public String getWord() {
        return word;
    }

    @Override
    public int compareTo(WordCount wc) {
        return wc.getCount().compareTo(count);
    }

    @Override
    public String toString() {
        return word + ": " + count;
    }

}
