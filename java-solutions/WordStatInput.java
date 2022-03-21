import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class WordStatInput {
    public static boolean isWord(char s) {
        return Character.isLetter(s) || Character.getType(s) == Character.DASH_PUNCTUATION || s == '\'';
    }
	public static void main(String[] args) {
	    try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
	        String str = in.readLine();
	        ArrayList<Pair> words = new ArrayList<Pair>();
	        while(str != null) {
	            int indexStart = -1, indexEnd = -1;
	            for (int i = 0; i < str.length(); i++) {
	                if ( isWord(str.charAt(i)) ) {
	                    if (indexStart == -1) {
						indexStart = i;
					    }
					    indexEnd = i;
	                }
	                if (indexStart != -1 && (!isWord(str.charAt(i)) || (i + 1 == str.length())) ) {
	                    String tempS = str.substring(indexStart,indexEnd + 1).toLowerCase();
	                    boolean tempOk = true;
	                    for(int j = 0; j < words.size(); j++) {
	                        if (words.get(j).word.equals(tempS)) {
	                            words.get(j).cnt++;
	                            tempOk = false;
	                            break;
	                        }
	                    }
	                    if (tempOk) {
	                        Pair tempPair = new Pair(tempS, 1);
	                        words.add(tempPair);
	                    }
					    indexStart = -1;
				    }
	            }
	            str = in.readLine();
	        }
	        in.close();
	        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
	        for(int i = 0; i < words.size(); i++) {
	            out.write(words.get(i).word + " " + words.get(i).cnt + "\n");
	        }
	        out.close();
	    } catch (FileNotFoundException e) {
            System.out.println("file not found " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ioexception " + e.getMessage());
        } 
	}
}