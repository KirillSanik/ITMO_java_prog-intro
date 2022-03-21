package md2html;

import java.util.List;

public class Text extends Paragraph {
    private String text;
    private boolean listOk = false;

    public Text(String text) {
        super(text);
        this.text = text;
    }

    public void putList(List<MarHtml> marHtmls) {
        super.putList(marHtmls);
        listOk = true;
    }

    @Override
    public boolean getType() {
        if (listOk) {
            return true;
        }
        return false;
    }

    @Override
    public void toText(StringBuilder str) {
        if (listOk) {
            super.toText(str);
        }
        else {
            str.append(text);
        }
    }

    @Override
    public void toHtml(StringBuilder str) {
        if (listOk) {
            super.toHtml(str);
        }
        else {
            str.append(text);
        }
    }
}
