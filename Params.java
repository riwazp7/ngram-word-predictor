/** Params.java
 *  Class with static variable that specify the Predictor's parameters.
 *  (c) Julian Vera, Riwaz Poudyal 2016
 */
public class Params {

    // Array of files to initially train the predictor with
    public static final String[] TRAINING_FILES = new String[] {"empty.txt"};

    // Unprintable String that represents non-existent parts of an N-gram.
    public static final String BLANK = "BLANK";

    // File with words to filter.
    public static final String WORDS_TO_FILTER = "curseWords.txt";

    // The highest level nodes in the tree will be sorted after this many updates. Lower ones will be sorted
    // after this number / ~(level) ^ 2 updates.
    public static final int SORT_THRESHOLD_FACTOR = 20;

    // Number of word suggestions to return when asked for a prediction
    public static final int NO_OF_SUGGESTIONS = 5;

}
