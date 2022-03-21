package md2html;

import java.util.List;

public class Strong extends Paragraph {

    public Strong(List<MarHtml> marHtmls) {
        super(marHtmls);
    }

    // "__" or "**"
    @Override
    public void toHtml(StringBuilder str) {
        str.append("<strong>");
        super.toHtml(str);
        str.append("</strong>");
    }
}
