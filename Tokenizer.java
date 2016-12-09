/* Tokenizer.java
 * Divides a text file into words.
 * (c) 2016 Riwaz Poudyal and Julian Vera
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tokenizer {
  

    String corpus = new String;
    
    public static LinkedList<PentaGram> tokenize(String filename)
	          throws FileNotFoundException {
	
	Scanner fileScanner = new Scanner(new File(filename));
	LinkedList<PentaGram> result = new LinkedList<PentaGram>();
	//store all strings in result list
	while (fileScanner.hasNextLine()) {	  
	    corpus += fileScanner.nextLine();
	}

	String[] sentences = corpus.split("\\.");
	String first = "\1";
	String second = "\2";
	String third = "\3";
	String fourth = "\4";

	for(int i = 0; i < sentences.length; i++){
		String[] words = sentences[i].split(" ");
		for(int j = 0; j < words.length; j++){
			if( j == 0 ){
		    	PentaGram p = new PentaGram(first,second,third,fourth,words[j]);
		  	} else if ( j == 1 ) {
		    	PentaGram p = new PentaGram(second,third,fourth,words[j-1],words[j]);
		  	} else if ( j == 2 ) {
		    	PentaGram p = new PentaGram(third,fourth,words[j-2],words[j-1],words[j]);
		  	} else if ( j == 3 ){
		    	PentaGram p = new PentaGram(fourth,words[j-3],words[j-2],words[j-1],words[j]);
		  	} else {
		    	PentaGram p = new PentaGram(words[j-4],words[j-3],words[j-2],words[j-1],words[j]);
		  	}
	   	}
		result.add(p);
	}
      return pentagram;
	}
    
    public static void main(String[] args) {
	// TEST
	
    }
    
}
