package md2html;

import java.util.List;

public class Code extends Paragraph {

    public Code(List<MarHtml> marHtmls) {
        super(marHtmls);
    }

    // "`"
    @Override
    public void toHtml(StringBuilder str) {
        str.append("<code>");
        super.toHtml(str);
        str.append("</code>");
    }
}