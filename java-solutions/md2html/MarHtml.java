package md2html;

import java.util.List;

public interface MarHtml {

    public void toText(StringBuilder str);
    public boolean getType();
    public void putList(List<MarHtml> marHtmls);
    public void reConstruct();
    public void toHtml(StringBuilder str);
}
