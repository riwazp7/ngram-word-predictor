/* Tokenizer.java
 * Divides a text file into words.
 * (c) 2016 Riwaz Poudyal and Julian Vera
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Tokenizer {
    
    public static LinkedList<PentaGram> tokenize(String filename)
	          throws FileNotFoundException {
	
		Scanner fileScanner = new Scanner(new File(filename));
		LinkedList<PentaGram> result = new LinkedList<>();
		String corpus = "";

		//store all strings in result list
		while (fileScanner.hasNextLine()) {
	    	corpus += fileScanner.nextLine();
		}

		String[] sentences = corpus.split("\\.");
		String filler = "\1";

		for(int i = 0; i < sentences.length; i++){
			PentaGram p;
	    	String[] words = sentences[i].split(" ");
	    	for(int j = 0; j < words.length; j++){
				if( j == 0 ){
	    			if(words[j].equals("")) {
						p = new PentaGram(new String[] {filler,filler,filler,filler,words[++j]});
					} else {
						p = new PentaGram(new String[]{filler, filler, filler, filler, words[j]});
					}
				} else if ( j == 1 ) {
		    		p = new PentaGram(new String[] {filler,filler,filler,words[j-1],words[j]});
				} else if ( j == 2 ) {
		    		p = new PentaGram(new String[] {filler,filler,words[j-2],words[j-1],words[j]});
				} else if ( j == 3 ){
		    		p = new PentaGram(new String[] {filler,words[j-3],words[j-2],words[j-1],words[j]});
				} else {
					p = new PentaGram(new String[]{words[j - 4], words[j - 3], words[j - 2], words[j - 1], words[j]});
				}
				result.add(p);
	    	}
		}
		return result;
	}
    
    public static void main(String[] args) throws FileNotFoundException {
	// TEST
		LinkedList<PentaGram> gram = Tokenizer.tokenize("test.txt");
		for(int i = 0; i < gram.size(); i++) {
			PentaGram p = gram.get(i);
			p.printWords();
		}

	}
    
}
