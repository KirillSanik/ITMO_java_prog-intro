import java.util.*;
import java.io.*;

public class Scanner {
    private Reader in;
    private char[] buffer = new char[2048];

    private String lastStr = null;
    private Integer lastInt = null;
    private String lastNext = null;
    private String lastWord = null;

    private int currentBuffInd = -1;
    private int currentBuffEnd = 0;
    private int cntStrWord = 1;
    private int cntStr = 1;
    
    private String separator = System.lineSeparator();
    private int n = separator.length();
    private char[] separators = separator.toCharArray();

    public Scanner(String file, String encoding) throws IOException {
        in = new InputStreamReader(new FileInputStream(file), encoding);
    }

    public Scanner(InputStream input) throws IOException {
        in = new InputStreamReader(input);
    }

    public Scanner(String str) {
        in = new StringReader(str);
    }

    public void close() throws IOException {
        in.close();
    }
    
    public int numStr() {
        return cntStrWord;
    }
    
    private static boolean isWord(char s) {
        return Character.isLetter(s) || Character.getType(s) == Character.DASH_PUNCTUATION || s == '\'';
    }

    private static boolean isNum(char s) {
        return Character.isDigit(s) || (int) s == (int) '-'
                || ((int) Character.toLowerCase(s) >= (int) 'a' && (int) Character.toLowerCase(s) <= (int) 'f');
    }

    private boolean hasNext(String type) throws IOException {
        if (lastStr != null) {
            StringBuilder str = new StringBuilder();
            boolean okNext = false;
            for (int i = 0; i < lastStr.length(); i++) {
                boolean intTemp = isNum(lastStr.charAt(i));
                boolean wordTemp = isWord(lastStr.charAt(i));
                if (type.equals("int")) {
                    wordTemp = false;
                } else if (type.equals("word")) {
                    intTemp = false;
                }
                 if (isSeparator(lastStr.charAt(i))) {
                    cntStr++;
                 }
                if (wordTemp || intTemp) {
                    cntStrWord = cntStr;
                    okNext = true;
                    str.append(lastStr.charAt(i));
                } else if (okNext) {
                    currentBuffInd = i + 1;
                    break;
                }
            }
            if (str.length() > 0) {
                lastNext = str.toString();
                return true;
            }
        }
        if (lastNext != null) {
            return true;
        } else {
            StringBuilder str = new StringBuilder();
            if (currentBuffInd == -1) {
                currentBuffEnd = in.read(buffer);
                currentBuffInd = 0;
                if (currentBuffEnd == -1) {
                    return false;
                }
            }
            boolean okNext = false;
            while (currentBuffEnd != -1) {
                boolean ok = false;
                for (int i = currentBuffInd; i < currentBuffEnd; i++) {
                    boolean intTemp = isNum(buffer[i]);
                    boolean wordTemp = isWord(buffer[i]);
                    if (type.equals("int")) {
                        wordTemp = false;
                    } else if (type.equals("word")) {
                        intTemp = false;
                    }
                    if (isSeparator(buffer[i])) {
                        cntStr++;
                    }
                    if (wordTemp || intTemp) {
                        cntStrWord = cntStr;
                        okNext = true;
                        str.append(buffer[i]);
                    } else if (okNext) {
                        currentBuffInd = i + 1;
                        ok = true;
                        break;
                    }
                }
                if (ok) {
                    break;
                }
                currentBuffEnd = in.read(buffer);
                currentBuffInd = 0;
            }
            if (str.length() == 0) {
                return false;
            }
            lastNext = str.toString();
            return true;
        }
    }

    public String nextWord() throws IOException {
        if (lastWord != null) {
            String tempWord = lastWord;
            lastWord = null;
            return tempWord;
        } else if (this.hasNextWord()) {
            String tempWord = lastWord;
            lastWord = null;
            return tempWord;
        } else {
            return null;
        }
    }

    public boolean hasNextWord() throws IOException {
        if (lastWord != null) {
            return true;
        } else {
            if (this.hasNext("word")) {
                lastWord = lastNext;
                lastNext = null;
                return true;
            } else {
                return false;
            }
        }
    }

    public int nextInt(int osn) throws IOException {
        if (lastInt != null) {
            int tempInt = lastInt;
            lastInt = null;
            return tempInt;
        } else if (this.hasNextInt(osn)) {
            int tempInt = lastInt;
            lastInt = null;
            return tempInt;
        } else {
            return -1;
        }
    }

    public boolean hasNextInt(int osn) throws IOException {
        if (lastInt != null) {
            return true;
        } else {
            if (this.hasNext("int")) {
                if (osn > 10) {
                    lastInt = Integer.parseUnsignedInt(lastNext, 16);
                } else {
                    lastInt = Integer.parseInt(lastNext, osn);
                }
                lastNext = null;
                return true;
            } else {
                return false;
            }
        }
    }

    public String nextLine() throws IOException {
        if (lastStr != null) {
            String tempStr = lastStr;
            lastStr = null;
            return tempStr;
        } else if (this.hasNextLine()) {
            String tempStr = lastStr;
            lastStr = null;
            return tempStr;
        } else {
            return null;
        }
    }

    private boolean isSeparator(char s) {
        if (separator.length() == 1) {
            return s == separators[0];
        }
        return s == '\n';
        /*for (int i = 0; i < n; i++) {
            if (s == separators[i]) {
                return true;
            }
        }
        return false;*/
    }

    public boolean hasNextLine() throws IOException {
        if (lastStr != null) {
            return true;
        } else {
            StringBuilder str = new StringBuilder();
            if (currentBuffInd == -1) {
                currentBuffEnd = in.read(buffer);
                currentBuffInd = 0;
                if (currentBuffEnd == -1) {
                    return false;
                }
            }
            while (currentBuffEnd != -1) {
                boolean ok = false;
                for (int i = currentBuffInd; i < currentBuffEnd; i++) {
                    if (!isSeparator(buffer[i])) {
                        str.append(buffer[i]);
                    } else {
                        currentBuffInd = i + 1;
                        ok = true;
                        break;
                    }
                }
                if (ok) {
                    break;
                }
                currentBuffEnd = in.read(buffer);
                currentBuffInd = 0;
            }
            if (currentBuffEnd == -1) {
                return false;
            }
            lastStr = str.toString();
            return true;
        }
    }
}