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


    public static NGram getQuadGram(String words) {

		String filler = "\1";

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
}