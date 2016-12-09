public class Aligner {
    
    //Helper method for determining the min value in 2D array                                       
    private static int minimum(int a, int b, int c){
        return Math.min(Math.min(a,b),c);
    }
    
    public static int[][][] computeBackpointers( String s0, String s1 ) throws NullPointerException {
        if ( s0 == null || s1 == null ) throw new NullPointerException();

	int[][][] backptr = new int[s0.length()+1][s1.length()+1][2];

        /**                                                                                                      
         * Use a 2D array to help with determining the backpointers. The 2D array will be filled with all the edit               
         * distance values at each letter in both strings, using method from lecture.                 
	 **/
        int [][] arr = new int[s0.length()+1][s1.length()+1];

        for(int i = 0; i <= s0.length(); i++){ arr[i][0] = i; }

        for(int j = 0; j <= s1.length(); j++){ arr[0][j] = j; }

        for(int i = 1; i <= s0.length(); i++){
            for(int j = 1; j <= s1.length(); j++){
                arr[i][j] = minimum(
                                    arr[i-1][j] + 1,
                                    arr[i][j-1] + 1,
                                    arr[i-1][j-1] + ((s0.charAt(i-1) == s1.charAt(j-1)) ? 0 : 2));
            }
        }

        //Start at the end cell                                                                                      
        int i = s0.length();
        int j = s1.length();

	//To determine the coordinates of a backpointer, you find the min adjacent value                                                
        while( i > 0 && j > 0 ){
            int near_min = minimum( arr[i-1][j], arr[i][j-1], arr[i-1][j-1] );
            if( near_min == arr[i-1][j-1] ){
                backptr[i][j][0] = i - 1;
                backptr[i][j][1] = j - 1;
                i -= 1;
                j -= 1;
            } else if( near_min == arr[i][j-1] ){
                backptr[i][j][0] = i;
                backptr[i][j][1] = j - 1;
                j -= 1;
            } else{
                backptr[i][j][0] = i - 1;
                backptr[i][j][1] = j;
                i -= 1;
            }
        }

        return backptr;	
    }

    public static String[] align( String s0, String s1, int[][][] backptr ) {
        String[] result = new String[2];

        //Need to create two strings for proper alignment and to add to result array                                                    
        String result1 = "";
        String result2 = "";

        //These variables are used to get the necessary letters from a word                                                
        int coord1 = s0.length();
        int coord2 = s1.length();

        /**                                                                                                                                
         * Compare values in cells [i-1][j],[i][j-1],[i-1][j-1] given we're in cell [i][j]. 
	 * If [i-1][j-1] has the lowest value, align s[i] and t[j] then go to [i-1][j-1]        
	 * If [i][j-1] has lowest value, align t[j] with " " go to [i][j-1]                                                             
         * If [i-1][j] has lowest value, align s[i] with " " go to [i-1][j]  
         **/
        while( coord1 > 0 && 0 < coord2 ){

            int bp1 = backptr[coord1][coord2][0];
            int bp2 = backptr[coord1][coord2][1];

            if( (bp1 < coord1) && (bp2 < coord2) ){
                result1 = s0.charAt(coord1 - 1) + result1;
                result2 = s1.charAt(coord2 - 1) + result2;

            } else if( (bp1 == coord1) && (bp2 < coord2) ){
                result1 = " " + result1;
                result2 = s1.charAt(coord2 - 1) + result2;

            } else {
                result1 = s0.charAt(coord1 - 1) + result1;
                result2 = " " + result2;
            }
            coord1 = bp1;
            coord2 = bp2;
        }

	      //Add the necessary padding to a string                                                                                         
        if( coord1 == 0 ){
            result2 = s1.substring( 0, coord2 ) + result2;
            for(int i = 0; i < coord2; i++){
                result1 = " " + result1;
            }
        } else {
            result1 = s0.substring( 0, coord1 ) + result1;
            for(int i = 0; i < coord1; i++){
                result2 = " " + result2;
            }
        }

        //Reverse the string so it's not backwards                                                                                        
        String string1 = new StringBuffer(result1).reverse().toString();
        String string2 = new StringBuffer(result2).reverse().toString();
        result[0] = (string1);
        result[1] = (string2);

        return result;
    }
    
}
