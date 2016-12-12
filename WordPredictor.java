import java.io.FileNotFoundException;
import java.util.*;

public class WordPredictor {

	private static final String WORDS_TO_FILTER = Params.WORDS_TO_FILTER;

	private ProbTree tree;

	private HashMap<String, Integer> wordCountIndex = new HashMap<>();
	private ArrayList<WordCount> wordCounts = new ArrayList<>();

	public WordPredictor(String[] fileNames) throws FileNotFoundException {
		this.tree = new ProbTree(new Filter(WORDS_TO_FILTER));
		ArrayList<LinkedList<NGram>> nGrams = new ArrayList<>();
		for(String file : fileNames){
			LinkedList<NGram> NGram = Tokenizer.tokenizeToNgrams(file);
			nGrams.add(NGram);

			String[] words = Tokenizer.tokenizeToWords(file);
			for (String word : words) {
				if (wordCountIndex.containsKey(word)) {
					wordCounts.get(wordCountIndex.get(word)).incrementCount();
				} else {
					WordCount wc = new WordCount(word, 1);
					wordCounts.add(wc);
					wordCountIndex.put(word, wordCounts.size() - 1);
				}
			}
		}

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

	public void addString(String str) {
		addSentences(InputProcessor.processInputString(str));
	}

	public void addSentences(String[] sentences) {
		if (sentences == null || sentences.length == 0) return;
		for (String sentence : sentences) {
			List<NGram> nGrams = NGram.getNGramsFromSentence(sentence);
			for (NGram nGram : nGrams) {
				addNgram(nGram);
			}
			addWords(sentence);
		}
	}

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

	private void addNgram(NGram n) {
		tree.add(n);
	}

	public static void main(String[] args) {
		try {
			WordPredictor predictor = new WordPredictor(new String[]{"small_test.txt"});
			System.out.println(predictor.getPrediction("the"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}