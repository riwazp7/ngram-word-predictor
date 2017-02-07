/** WordPredictor.java
 *  Back-end of a n-gram based word predictor
 *  (c) Riwaz Poudyal, Julian Vera 2016
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class WordPredictor {

	// Name of a file containing words to filter
	private static final String WORDS_TO_FILTER = Params.WORDS_TO_FILTER;

	private ProbTree tree;

	// Track words we have seen and maintain a frequency
	private HashMap<String, Integer> wordCountIndex = new HashMap<>();
	private ArrayList<WordCount> wordCounts = new ArrayList<>();

	// Takes a list of file names to initially train the predictor on
	public WordPredictor(String[] fileNames) throws FileNotFoundException {
		this.tree = new ProbTree(new Filter(WORDS_TO_FILTER));
		ArrayList<LinkedList<NGram>> nGrams = new ArrayList<>();
		for(String file : fileNames){
			LinkedList<NGram> NGram = Tokenizer.tokenizeToNgrams(file);
			nGrams.add(NGram);

			String[] words = Tokenizer.tokenizeToWords(file);
			for (String word : words) {
				if (word.trim().isEmpty()) continue;
				if (wordCountIndex.containsKey(word)) {
					wordCounts.get(wordCountIndex.get(word)).incrementCount();
				} else {
					WordCount wc = new WordCount(word, 1);
					wordCounts.add(wc);
					wordCountIndex.put(word, wordCounts.size() - 1);
				}
			}
		}
		// Add all possible N-grams to the tree.
		for(LinkedList<NGram> penta : nGrams){
			for(NGram p : penta){
				tree.initialAdd(p);
			}
		}
		tree.initialSort();

		Collections.sort(wordCounts);
		for (int i = 0; i < wordCounts.size(); i++) {
			wordCountIndex.put(wordCounts.get(i).getWord(), i);
		}

	}

	public List<String> getPrediction(NGram n) {
		return tree.predictNextWords(n);
	}

	// Take a input string from the user, build a query N-gram from it, and call getPrediction(Ngram n)
	public List<String> getPrediction(String input) {
		if (input == null) return new ArrayList<>();
		input = input.trim();
		if (input.isEmpty()) {
			List<String> result = new ArrayList<>();
			for (int i = 0; i < 5 && i < wordCounts.size(); i++) {
				result.add(wordCounts.get(i).getWord());
			}
			return result;
		}
		return getPrediction(NGram.getQuadGram(input.toLowerCase()));
	}

	public List<String> getCompletion(String prefix) {
		return null;
	}

	// Process and  an input String from the user to the tree.
	public void addString(String str) {
		addSentences(InputProcessor.processInputString(str));
	}

	// Adds Array of processed sentences to the tree.
	public void addSentences(String[] sentences) {
		if (sentences == null || sentences.length == 0) return;
		for (String sentence : sentences) {
			List<NGram> nGrams = NGram.getPentaGramsFromSentence(sentence);
			for (NGram nGram : nGrams) {
				addNgram(nGram);
			}
			addWords(sentence);
		}
	}

	// Takes a sentence, splits it by space, and adds the words to word count
	private void addWords(String sentence) {
		String[] words = sentence.split(" ");
		for (String word : words) {
			if (wordCountIndex.containsKey(word)) {
				WordCount wc = wordCounts.get(wordCountIndex.get(word));
				wc.incrementCount();

				int index;
				for (index = wordCountIndex.get(word); index > 0; index--) {
					if (wc.compareTo(wordCounts.get(index - 1)) < 0) {
						wordCounts.set(index, wordCounts.get(index - 1));
						wordCountIndex.put(wordCounts.get(index - 1).getWord(), index);
					} else {
						break;
					}
				}
				wordCounts.set(index, wc);
				wordCountIndex.put(wc.getWord(), index);
			} else {
				// Goes at the end of the list
				wordCounts.add(new WordCount(word, 1));
				wordCountIndex.put(word, wordCounts.size() - 1);
			}
		}
	}

	// Add a Ngram to the tree.
	private void addNgram(NGram n) {
		tree.add(n);
	}

	// ********* TEST ***********
	public static void main(String[] args) {
		try {
			WordPredictor predictor = new WordPredictor(new String[]{"small_test.txt"});
			System.out.println(predictor.getPrediction("the"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}