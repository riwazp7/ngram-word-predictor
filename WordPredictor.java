import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Stream;

public class WordPredictor {

	ArrayList<LinkedList<PentaGram>> pentaGrams;


	public WordPredictor(String[] fileNames) throws FileNotFoundException {
		pentaGrams = new ArrayList<LinkedList<PentaGram>>();
		for(String file : fileNames){
			LinkedList<PentaGram> pentaGram = Tokenizer.tokenize(file);
			pentaGrams.add(pentaGram);
		}
	}


    public QuadGram getQuadGram(String words) {

		String filler = "\1";

		words = words.replace(".", "");
		// store words in string array to easily determine number of words
		String[] splitWords = words.split(" ");

		QuadGram p = null;
		//determine if there are currently more than 4 words typed in
		int numWords = splitWords.length;
		if (numWords < 4) {
			if (numWords == 0) {
				p = null;
			} else if (numWords == 1) {
				p = new QuadGram(new String[]{filler, filler, filler, splitWords[0]});
			} else if (numWords == 2) {
				p = new QuadGram(new String[]{filler, filler, splitWords[0], splitWords[1]});
			} else if (numWords == 3) {
				p = new QuadGram(new String[]{filler, splitWords[0], splitWords[1], splitWords[2]});
			}
		} else {
			//just grab the last four words
			p = new QuadGram(new String[]{splitWords[numWords - 4], splitWords[numWords - 3],
						splitWords[numWords - 2], splitWords[numWords - 1]});
		}

		return p;
	}
}