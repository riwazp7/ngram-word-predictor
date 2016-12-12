import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WordPredictor {

	private static final String CURSE_WORDS = "curseWords.txt";

	private ProbTree tree;

	private HashMap<String, Integer> words = new HashMap<>();

	public WordPredictor(String[] fileNames) throws FileNotFoundException {
		this.tree = new ProbTree(new Filter(CURSE_WORDS));
		ArrayList<LinkedList<NGram>> nGrams = new ArrayList<>();
		for(String file : fileNames){
			LinkedList<NGram> NGram = Tokenizer.tokenizeToNgrams(file);
			nGrams.add(NGram);
		}

		for(LinkedList<NGram> penta : nGrams){
			for(NGram p : penta){
				tree.add(p);
			}
		}
	}

	public List<String> getPrediction(NGram n) {
		return tree.predictNextWords(n);
	}

	public List<String> getPrediction(String input) {
		return getPrediction(NGram.getQuadGram(input.toLowerCase()));
	}

	public void addSentence(String sentence) {
		List<NGram> nGrams = NGram.getNGramsFromSentence(sentence);
		for (NGram nGram : nGrams) {
			addNgram(nGram);
		}
	}

	private void addNgram(NGram n) {
		tree.add(n);
	}

	public static void main(String[] args) {
		try {
			WordPredictor predictor = new WordPredictor(new String[]{"fixed.txt"});
			System.out.println(predictor.getPrediction("the"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}