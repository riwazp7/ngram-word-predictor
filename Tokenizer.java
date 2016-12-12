/** Tokenizer.java
 * Divides a text file into words or Ngrams.
 * (c) 2016 Riwaz Poudyal and Julian Vera
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Tokenizer {
    
    public static LinkedList<NGram> tokenizeToNgrams(String filename) throws FileNotFoundException {

		String[] sentences = getStringFromFile(filename).split("\\.");
		LinkedList<NGram> result = new LinkedList<>();

		for(int i = 0; i < sentences.length; i++) {
			sentences[i] = sentences[i].trim();
			result.addAll(NGram.getPentaGramsFromSentence(sentences[i].toLowerCase()));
		}
		return result;
	}

	public static String[] tokenizeToWords(String filename) throws FileNotFoundException {
		String[] words = getStringFromFile(filename).split(" ");
		return words;
	}

	public static String getStringFromFile(String filename) throws FileNotFoundException {
		Scanner fileScanner = new Scanner(new File(filename));
		String corpus = "";

		//store all strings in result list
		while (fileScanner.hasNextLine()) {
			corpus += fileScanner.nextLine();
		}
		return corpus;
	}

	// TEST
    public static void main(String[] args) throws FileNotFoundException {
		LinkedList<NGram> gram = Tokenizer.tokenizeToNgrams("fixed.txt");
		for(int i = 0; i < gram.size(); i++) {
			NGram p = gram.get(i);
			System.out.println(p);
		}

	}
    
}
