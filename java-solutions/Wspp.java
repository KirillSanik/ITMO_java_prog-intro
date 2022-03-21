import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Wspp {
    public static void main(String[] args) {
        Map<String, ArrayList<Integer>> words = new HashMap<String, ArrayList<Integer>>();
        List<String> firstInside = new ArrayList<String>();
        try {
            Scanner in = new Scanner(args[0], "UTF-8");
            int cnt = 1;
            while (in.hasNextWord()) {
                String tempS = in.nextWord().toLowerCase();
                ArrayList<Integer> tempVhod = new ArrayList<Integer>();
                if (words.containsKey(tempS) == false) {
                    firstInside.add(tempS);
                    tempVhod.add(cnt);
                    words.put(tempS, tempVhod);
                } else {
                    tempVhod = words.get(tempS);
                    tempVhod.add(cnt);
                    words.put(tempS, tempVhod);
                }
                cnt++;
            }
            try {
                //words.sort((pair1, pair2) -> Integer.valueOf(pair1.cnt).compareTo(Integer.valueOf(pair2.cnt)));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
                try {
                    for (int i = 0; i < firstInside.size(); i++) {
                        out.write(firstInside.get(i));
                        ArrayList<Integer> tempVhod = new ArrayList<Integer>();
                        tempVhod = words.get(firstInside.get(i));
                        out.write(" " + tempVhod.size());
                        for (int j = 0; j < tempVhod.size(); j++) {
                            out.write(" " + tempVhod.get(j));
                        }
                        out.newLine();
                    }
                } finally {
                    out.close();
                }
            } catch (FileNotFoundException e) {
                System.out.println("output file not found " + e.getMessage());
            } catch (IOException e) {
                System.out.println("output ioexception " + e.getMessage());
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