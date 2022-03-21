package md2html;

import java.util.List;

public class Emphasis extends Paragraph {

    public Emphasis(List<MarHtml> marHtmls) {
        super(marHtmls);
    }

    // "*" or "_"
    @Override
    public void toHtml(StringBuilder str) {
        str.append("<em>");
        super.toHtml(str);
        str.append("</em>");
    }
}
