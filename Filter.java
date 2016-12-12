/**
 * Reads a file that has every word on its own line. Put all the words in a HashSet
 * There's a wrapper method to determine if a word is in the HashSet
 **/

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class creates a filter from a file of words that we do not
 * want to predict (or suggest) to the user
 **/

public class Filter {
    
    HashSet<String> words;

    public Filter(String filename) throws FileNotFoundException {
		// Create the HashSet that will store all words
		words = new HashSet<>();
	
		Scanner fileScanner = new Scanner(new File(filename));

		while(fileScanner.hasNextLine()){
	    	words.add(fileScanner.nextLine());
		}

	}

    public boolean contains(String word){
	return words.contains(word);
    }


}