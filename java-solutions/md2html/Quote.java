package md2html;

import java.util.List;

public class Quote extends Paragraph {

    public Quote(List<MarHtml> marHtmls) {
        super(marHtmls);
    }

    // "\'\'"
    @Override
    public void toHtml(StringBuilder str) {
        str.append("<q>");
        super.toHtml(str);
        str.append("</q>");
    }
}