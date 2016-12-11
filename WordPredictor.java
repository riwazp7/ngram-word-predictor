import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class WordPredictor {

	private ArrayList<LinkedList<NGram>> nGrams;
	private ProbTree tree = new ProbTree();

	public WordPredictor(String[] fileNames) throws FileNotFoundException {
		nGrams = new ArrayList<>();
		for(String file : fileNames){
			LinkedList<NGram> NGram = Tokenizer.tokenize(file);
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
		return getPrediction(NGram.getQuadGram(input));
	}

	public void addNgram(NGram n) {
		tree.add(n);
	}

	public static void main(String[] args) {
		try {
			WordPredictor predictor = new WordPredictor(new String[]{"fixed.txt"});
			System.out.println(predictor.getPrediction(NGram.getQuadGram("Until No one can")));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
	}
}