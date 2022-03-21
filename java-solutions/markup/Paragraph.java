package markup;

import java.util.List;

public class Paragraph implements MarHtml {
    protected List<MarHtml> marHtmls;

    public Paragraph(List<MarHtml> marHtmls) {
        this.marHtmls = marHtmls;
    }

    @Override
    public void toMarkdown(StringBuilder str) {
        for (MarHtml mark : marHtmls) {
            mark.toMarkdown(str);
        }
    }

    @Override
    public void toHtml(StringBuilder str) {
        for (MarHtml html : marHtmls) {
            html.toHtml(str);
        }
    }
}
