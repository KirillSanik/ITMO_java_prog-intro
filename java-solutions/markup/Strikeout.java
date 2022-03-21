package markup;

import java.util.List;

public class Strikeout extends Paragraph {

    public Strikeout(List<MarHtml> marHtmls) {
        super(marHtmls);
    }

    @Override
    public void toMarkdown(StringBuilder str) {
        str.append("~");
        super.toMarkdown(str);
        str.append("~");
    }

    @Override
    public void toHtml(StringBuilder str) {
        str.append("<s>");
        super.toHtml(str);
        str.append("</s>");
    }
}
