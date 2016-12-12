/** InputProcessor.java
 *  Class with static methods to do *some* processing to a sentence.
 *  (c) Riwaz Poudyal, Julian Vera 2016
 */
public class InputProcessor {

    // Check if string is null or empty, trim white spaces, and replace punctuations with
    // appropriate ones.
    public static String[] processInputString(String sentence) {
        if (sentence == null) return null;
        sentence = sentence.trim().toLowerCase();
        if (sentence.isEmpty()) return null;
        sentence = sentence.replaceAll("[?!:]", ".");
        sentence = sentence.replaceAll("[,\"\\]\\[\\}\\{\\)\\(\\<\\>/]", "");
        return sentence.split("\\.");
    }

    // TEST
    public static void main(String[] args) {
        String sentence = "hey asdf? w(ha)t [the] {project!";
        String[] sentenceA = InputProcessor.processInputString(sentence);
    }

}
