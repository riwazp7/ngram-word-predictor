import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class WordPredictor {

	private ArrayList<LinkedList<NGram>> nGrams;
	private ProbTree tree = new ProbTree();

	Filter curseWords = new Filter();
	HashSet<String> badWords = curseWords.words;
	String curseWordsFile = "curseWords.txt";

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

		Scanner fileScanner = new Scanner(new File(curseWordsFile));
		while(fileScanner.hasNextLine()){
			badWords.add(fileScanner.nextLine());
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