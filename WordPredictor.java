public class WordPredictor {

    public void userPentaGram(String words){
	
	String first = "\1";
	String second = "\2";
	String third = "\3";
	String fourth = "\4";

	words = words.replace(".","");
	// store words in string array to easily determine number of words
	String[] splitWords = words.split(" ");

	//determine if there are currently more than 5 words typed in
	int numWords = splitWords.length;
	if(numWords < 5) {
	    if(numWords == 0){
		PentaGram p = null;
	    }
	    if(numWords == 1) {
		PentaGram p = new PentaGram(new String[] {first,second,third,fourth,splitWords[0]});
	    } else if (numWords == 2){
		PentaGram p = new PentaGram(new String[] {second,third,fourth,splitWords[0],splitWords[1]});
	    } else if (numWords == 3){
		PentaGram p = new PentaGram(new String[] {third,fourth,splitWords[0],splitWords[1],splitWords[2]});
	    } else if (numWords == 4){
		PentaGram p = new PentaGram(new String[] {fourth,splitWords[0],splitWords[1],splitWords[2],splitWords[3]});
	    }
	} else if(numWords == 5){
	    PentaGram p
				= new PentaGram(new String[] {splitWords[0],splitWords[1],splitWords[2],splitWords[3],splitWords[4]});
	} else {
	    //just grab the last five words
	    PentaGram p = new PentaGram(new String[] {splitWords[numWords-5],splitWords[numWords-4],
				splitWords[numWords-3],splitWords[numWords-2],splitWords[numWords-1]});
	}
    }


}