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

  public static List<String> tokenize(String filename)
                              throws FileNotFoundException {
    List<String> result = new ArrayList<String>();
    Scanner fileScanner = new Scanner(new File(filename));
    while (fileScanner.hasNext()) {
      result.add(fileScanner.next());
    }
    return result;
  }

  public static void main(String[] args) {
    // TEST

  }

}
