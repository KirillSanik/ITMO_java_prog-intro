import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class WsppSortedPosition {
    public static void main(String[] args) {
        Map<String, ArrayList<Pair>> words = new TreeMap<String, ArrayList<Pair>>();
        try {
            Scanner in = new Scanner(args[0], "UTF-8");
            int cnt = 1;
            int cntStr = 1;
            while (in.hasNextWord()) {
                String tempS = in.nextWord().toLowerCase();
                if (in.numStr() > cntStr) {
                    cnt = 1;
                    cntStr = in.numStr();
                }
                Pair tempPair = new Pair(cntStr, cnt);
                ArrayList<Pair> tempVhod = new ArrayList<Pair>();
                if (!words.containsKey(tempS)) {
                    tempVhod.add(tempPair);
                    words.put(tempS, tempVhod);
                } else {
                    tempVhod = words.get(tempS);
                    tempVhod.add(tempPair);
                    words.put(tempS, tempVhod);
                }
                cnt++;
            }
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
                try {
                    for (String key : words.keySet()) {
                        ArrayList<Pair> tempVhod = new ArrayList<Pair>();
                        tempVhod = words.get(key);
                        out.write(key + " " + tempVhod.size());
                        for (int i = 0; i < tempVhod.size(); i++) {
                            out.write(" " + tempVhod.get(i).str + ":" + tempVhod.get(i).cnt);
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