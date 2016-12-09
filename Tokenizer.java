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
  
  //HashMap that stores word and index it appears in the file
  HashMap<String,ArrayList<Integer>> indexedWords = new HashMap<String,ArrayList<Integer>>();
  int totalWords = 0;
  
  public static List<String> tokenize(String filename)
                              throws FileNotFoundException {
    List<String> result = new ArrayList<String>();
    
    Scanner fileScanner = new Scanner(new File(filename));
    
    //used to index words in HashMap
    int index = 0;
      
    while (fileScanner.hasNext()) {
      String word = fileScanner.next();      
      if(!indexedWords.containsKey(word)){
        ArrayList<Integer> indices = new ArrayList<Integer>();
        indices.add(index++);
        indexedWords.put(word,indices);
      } else {
        ArrayList<Integer> indices = indexedWords.get(word);
        indices.add(index++);
        indexedWords.put(word,indices);  
      }      
      result.add(word);
      totalWords += 1;
    }
    return result;
  }

  public static void main(String[] args) {
    // TEST

  }

}
