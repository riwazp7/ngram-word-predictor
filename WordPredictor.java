import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordPredictor {

	private static final String CURSE_WORDS = "curseWords.txt";
	private static final String SAVED_TREE_DIR = "SAVED_TREE.txt";

	private ArrayList<LinkedList<NGram>> nGrams;
	private ProbTree tree = new ProbTree();

	Filter curseWordsFilter;


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

		this.curseWordsFilter = new Filter(CURSE_WORDS);
		writeTreetoFile();
	}

	public WordPredictor() {
		try {
			String treeString = new String(Files.readAllBytes(Paths.get(SAVED_TREE_DIR)));
			Gson gson = new Gson();
			this.tree = gson.fromJson(treeString, ProbTree.class);
		} catch (IOException e) {
			throw new RuntimeException("Error reading tree from file: " + SAVED_TREE_DIR);
		}
	}

	private boolean writeTreetoFile() {
		Gson gson = new Gson();
		String treeString = gson.toJson(tree);
		try {
			PrintWriter printWriter = new PrintWriter(SAVED_TREE_DIR);
			printWriter.print(treeString);
			printWriter.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
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
			WordPredictor predictor = new WordPredictor(new String[]{"small_test.txt"});
			System.out.println(predictor.getPrediction(NGram.getQuadGram("Until No one can see")));

			WordPredictor predictor1 = new WordPredictor();
			System.out.println(predictor.getPrediction(NGram.getQuadGram("Until No one can see")));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
	}
}