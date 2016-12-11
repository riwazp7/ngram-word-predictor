/* Tokenizer.java
 * Divides a text file into words.
 * (c) 2016 Riwaz Poudyal and Julian Vera
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Tokenizer {
    
    public static LinkedList<NGram> tokenize(String filename) throws FileNotFoundException {
	
		Scanner fileScanner = new Scanner(new File(filename));
		LinkedList<NGram> result = new LinkedList<>();
		String corpus = "";

		//store all strings in result list
		while (fileScanner.hasNextLine()) {
	    	corpus += fileScanner.nextLine();
		}

		String[] sentences = corpus.split("\\.");

		for(int i = 0; i < sentences.length; i++) {
			sentences[i] = sentences[i].trim();
			result.addAll(NGram.getNGramsFromSentence(sentences[i].toLowerCase()));
		}
		return result;
	}

	// TEST
    public static void main(String[] args) throws FileNotFoundException {
		LinkedList<NGram> gram = Tokenizer.tokenize("small_test.txt");
		for(int i = 0; i < gram.size(); i++) {
			NGram p = gram.get(i);
			System.out.println(p);
		}

	}
    
}
