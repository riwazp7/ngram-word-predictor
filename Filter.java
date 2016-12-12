/** Filter.java
 * (c) Julian Vera, Riwaz Poudyal
 * This class creates a filter from a file of words that we do not
 * want to predict (or suggest) to the user
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

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