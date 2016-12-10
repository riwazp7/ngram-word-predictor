/**
 * Reads a file that has every word on its own line. Put all the words in a HashSet
 * There's a wrapper method to determine if a word is in the HashSet
 **/

public class Filter {
    
    public void Filter() {

	// Create the HashSet that will store all words
	HashSet words = new HashSet();
	
	Scanner fileScanner = new Scanner(new File(filename));
	while(fileScanner.hasNextLine()){
	    words.add(fileScanner.nextLine());
	}

    }

    public boolean contains(String word){
	return words.contains(word);
    }


}