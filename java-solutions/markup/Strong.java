package markup;

import java.util.List;

public class Strong extends Paragraph {

    public Strong(List<MarHtml> marHtmls) {
        super(marHtmls);
    }

    @Override
    public void toMarkdown(StringBuilder str) {
        str.append("__");
        super.toMarkdown(str);
        str.append("__");
    }

    @Override
    public void toHtml(StringBuilder str) {
        str.append("<strong>");
        super.toHtml(str);
        str.append("</strong>");
    }
}
