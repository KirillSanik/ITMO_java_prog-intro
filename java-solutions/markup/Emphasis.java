package markup;

import java.util.List;

public class Emphasis extends Paragraph {

    public Emphasis(List<MarHtml> marHtmls) {
        super(marHtmls);
    }

    @Override
    public void toMarkdown(StringBuilder str) {
        str.append("*");
        super.toMarkdown(str);
        str.append("*");
    }

    @Override
    public void toHtml(StringBuilder str) {
        str.append("<em>");
        super.toHtml(str);
        str.append("</em>");
    }
}
