/**
 * Created by Riwaz on 12/12/16.
 */
public class InputProcessor {

    public static String[] processInputString(String sentence) {
        if (sentence == null) return null;
        sentence = sentence.trim();
        if (sentence.isEmpty()) return null;
        sentence = sentence.replaceAll("[?!:]", ".");
        sentence = sentence.replaceAll("[,\"\\]\\[\\}\\{\\)\\(\\<\\>/]", "");
        return sentence.split("\\.");
    }

    // TEST
    public static void main(String[] args) {
        String sentence = "hey man? w(ha)t [the] {project!";
        String[] sentenceA = InputProcessor.processInputString(sentence);
    }

}
