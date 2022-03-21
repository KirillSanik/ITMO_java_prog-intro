package md2html;

import java.util.*;
import java.io.*;

public class Md2Html {
    public static void main(String[] args) {
        try {
           Scanner in = new Scanner(args[0], "UTF-8");
           // Scanner in = new Scanner(System.in);
            try {
                //input
                ArrayList<String> paragraphs = new ArrayList<String>();
                while (in.hasNextLine()) {
                    StringBuilder str = new StringBuilder();
                    String tempStr;
                    do {
                        tempStr = in.nextLine();
                        str.append(tempStr + System.lineSeparator());
                    } while (in.hasNextLine() && tempStr.length() > 0 && !in.isSeparator(tempStr.charAt(0)));
                    if (in.isSeparator(str.charAt(0))) {
                        continue;
                    }
                    int separate;
                    for (separate = str.length() - 1; in.isSeparator(str.charAt(separate)); separate--) {
                    }
                    String textStr = str.substring(0, separate + 1);
                    textStr = textStr.replace("&", "&amp;");
                    textStr = textStr.replace("<", "&lt;");
                    textStr = textStr.replace(">", "&gt;");
                    boolean isParahraph = true;
                    Paragraph markdown;
                    int cntH = 1;
                    if ((int) textStr.charAt(0) == '#') {
                        isParahraph = false;
                    }
                    if (isParahraph) {
                        markdown = new Paragraph(List.of(new Text(textStr)));
                    } else {
                        while ((int) textStr.charAt(cntH) == '#') {
                            cntH++;
                        }
                        int index;
                        for (index = cntH; Character.isWhitespace(textStr.charAt(index)); index++) {
                        }
                        if (cntH == index) {
                            isParahraph = true;
                            //System.err.println(textStr);
                            markdown = new Paragraph(List.of(new Text(textStr)));
                        } else {
                            //System.err.println(textStr.substring(index));
                            markdown = new Paragraph(List.of(new Text(textStr.substring(index))));
                        }
                    }

                    markdown.reConstruct();
                    StringBuilder res = new StringBuilder();
                    if (isParahraph) {
                        res.append("<p>");
                        markdown.toHtml(res);
                        res.append("</p>");
                    } else {
                        String cntHstr = String.valueOf(cntH);
                        res.append("<h" + cntHstr + ">");
                        markdown.toHtml(res);
                        res.append("</h" + cntHstr + ">");
                    }
                    String temp = res.toString().replace("\\", "") + System.lineSeparator();
                    //System.err.println(res);
                    paragraphs.add(temp);
                }

                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
                try {
                    for (int i = 0; i < paragraphs.size(); i++) {
                        out.write(paragraphs.get(i));
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
