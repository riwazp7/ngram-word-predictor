/**
 * A Node object is going to have a parent, word and frequency
**/

public class Node {

  String word;
  int frequency;
  Node parent;

  class Node(Node parent, String word, Int frequency) {
    this.parent = parent;
    this.word = word;
    this.frequency = frequency;
  }

  public String getWord() {
  	return word;
  }
  
  public Node parent() {
    return parent;
  }

}
