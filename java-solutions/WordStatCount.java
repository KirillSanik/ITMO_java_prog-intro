import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class WordStatCount {
    public static boolean isWord(char s) {
        return Character.isLetter(s) || Character.getType(s) == Character.DASH_PUNCTUATION || s == '\'';
    }

    public static void main(String[] args) {
        ArrayList<Pair> words = new ArrayList<Pair>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            try {
                String str = in.readLine();
                while (str != null) {
                    int indexStart = -1, indexEnd = -1;
                    for (int i = 0; i < str.length(); i++) {
                        if (isWord(str.charAt(i))) {
                            if (indexStart == -1) {
                                indexStart = i;
                            }
                            indexEnd = i;
                        }
                        if (indexStart != -1 && (!isWord(str.charAt(i)) || (i + 1 == str.length()))) {
                            String tempS = str.substring(indexStart, indexEnd + 1).toLowerCase();
                            boolean tempOk = true;
                            for (int j = 0; j < words.size(); j++) {
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
            try {
            words.sort((pair1, pair2) -> Integer.valueOf(pair1.cnt).compareTo(Integer.valueOf(pair2.cnt)));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
            try {
                for (int i = 0; i < words.size(); i++) {
                    out.write(words.get(i).word + " " + words.get(i).cnt);
                    out.newLine();
                }
            } finally {
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("output file not found " + e.getMessage());
        } catch (IOException e) {
            System.out.println("output ioexception " + e.getMessage());
        }
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("input file not found " + e.getMessage());
        } catch (IOException e) {
            System.out.println("input ioexception " + e.getMessage());
        }
        
    }
}