package md2html;

import java.util.ArrayList;
import java.util.List;

public class Paragraph implements MarHtml {
    protected List<MarHtml> marHtmls;

    public Paragraph(List<MarHtml> marHtmls) {
        this.marHtmls = marHtmls;
    }

    protected Paragraph(String text) {
    }

    public void putList(List<MarHtml> marHtmls) {
        this.marHtmls = marHtmls;
    }

    protected boolean isMarkdown(char simb) {
        switch ((int) simb) {
            case '-':
            case '_':
            case '*':
            case '`':
            case '\'':
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean getType() {
        return true;
    }

    @Override
    public void reConstruct() {
        // MarHtml reCons : marHtmls
        for (int i = 0; i < marHtmls.size(); i++) {
            if (marHtmls.get(i).getType()) {
                marHtmls.get(i).reConstruct();
            } else {
                int indexMarkStart = 0;
                while (true) {
                    StringBuilder str = new StringBuilder();
                    marHtmls.get(i).toText(str);
                    boolean doubleSimb = false;
                    while ((indexMarkStart < str.length()) && !isMarkdown(str.charAt(indexMarkStart))) {
                        if ((int) str.charAt(indexMarkStart) == '\\') {
                            if (indexMarkStart < str.length() - 1 && (int) str.charAt(indexMarkStart + 1) == '\'') {
                                indexMarkStart++;
                            }
                            indexMarkStart++;
                        }
                        indexMarkStart++;
                        if (indexMarkStart < str.length() - 1) {
                            if ((int) str.charAt(indexMarkStart) == '\'' && (int) str.charAt(indexMarkStart + 1) == '\\') {
                                indexMarkStart += 3;
                            } else {
                                if ((int) str.charAt(indexMarkStart) == '-' && (int) str.charAt(indexMarkStart + 1) != '-') {
                                    indexMarkStart += 2;
                                }
                                if ((int) str.charAt(indexMarkStart) == '\'' && (int) str.charAt(indexMarkStart + 1) != '\'') {
                                    indexMarkStart += 2;
                                }
                            }
                        }
                    }
                    if (indexMarkStart == str.length()) {
                        break;
                    }
                    if (indexMarkStart + 1 < str.length() && str.charAt(indexMarkStart) == str.charAt(indexMarkStart + 1)) {
                        doubleSimb = true;
                        indexMarkStart++;
                    }
                    char simbNeed = str.charAt(indexMarkStart);
                    int indexMarkEnd = indexMarkStart + 1;
                    String left, center, right;
                    if (doubleSimb) {
                        while ((indexMarkEnd < str.length() - 1) && !((simbNeed == str.charAt(indexMarkEnd)) && (simbNeed == str.charAt(indexMarkEnd + 1)))) {
                            indexMarkEnd++;
                        }
                        if (indexMarkEnd >= str.length()) {
                            indexMarkStart++;
                            continue;
                        }
                        left = str.substring(0, indexMarkStart - 1);
                        if (indexMarkEnd + 2 >= str.length())  {
                            right = "";
                        } else {
                            right = str.substring(indexMarkEnd + 2);
                        }
                    } else {
                        if (indexMarkEnd + 1 < str.length() && simbNeed == str.charAt(indexMarkEnd) && simbNeed == str.charAt(indexMarkEnd + 1)) {
                            indexMarkEnd += 2;
                        }
                        while (indexMarkEnd < str.length() && !(simbNeed == str.charAt(indexMarkEnd))) {
                            if ((int) str.charAt(indexMarkEnd) == '\\') {
                                indexMarkEnd++;
                            }
                            indexMarkEnd++;
                            if (indexMarkEnd < str.length() - 1) {
                                if (simbNeed == str.charAt(indexMarkEnd) && simbNeed == str.charAt(indexMarkEnd + 1)) {
                                    indexMarkEnd += 2;
                                }
                            }
                        }
                        if (indexMarkEnd >= str.length()) {
                            indexMarkStart++;
                            continue;
                        }
                        left = str.substring(0, indexMarkStart);
                        right = str.substring(indexMarkEnd + 1);
                    }
                    center = str.substring(indexMarkStart + 1, indexMarkEnd);
                    //System.err.println(" : " + left + " : " + center + " : " + right + " : ");
                    List<MarHtml> tempModuls;
                    switch ((int) simbNeed) {
                        case '*':
                        case '_':
                            if (doubleSimb) {
                                tempModuls = List.of(new Text(left), new Strong(List.of(new Text(center))), new Text(right));
                            } else {
                                tempModuls = List.of(new Text(left), new Emphasis(List.of(new Text(center))), new Text(right));
                            }
                            break;
                        case '-':
                            tempModuls = List.of(new Text(left), new Strikeout(List.of(new Text(center))), new Text(right));
                            break;
                        case '`':
                            tempModuls = List.of(new Text(left), new Code(List.of(new Text(center))), new Text(right));
                            break;
                        case '\'':
                            tempModuls = List.of(new Text(left), new Quote(List.of(new Text(center))), new Text(right));
                            break;
                        default:
                            tempModuls = List.of(new Text(str.toString()));
                    }
                    marHtmls.get(i).putList(tempModuls);
                    i--;
                    break;
                }
            }
        }
    }

    @Override
    public void toText(StringBuilder str) {
        for (MarHtml mark : marHtmls) {
            mark.toText(str);
        }
    }

    @Override
    public void toHtml(StringBuilder str) {
        for (MarHtml html : marHtmls) {
            html.toHtml(str);
        }
    }
}
