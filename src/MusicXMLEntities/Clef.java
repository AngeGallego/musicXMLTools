package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Clef extends MusicElement {

    private String mLine = "";
    private String mSign = "";

    public Clef(Element xmlKey) {
        NodeList components = XMLUtils.getComponents(xmlKey, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "sign":
                    mSign = current.getTextContent();
                    break;
                case "line":
                    mLine = current.getTextContent();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Clef.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Clef clef = (Clef) obj;
        return clef.mSign.equals(mSign) && clef.mLine.equals(mLine);
    }

    @Override
    public int compareTo(MusicElement element) {
        return equals(element) ? 0 : Comparator.MAX_SUBSTITUTION_COST;
    }

    @Override
    public ArrayList<ComparisonResult> inDepthComparison(MusicElement element) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Clef{" +
                "mLine='" + mLine + '\'' +
                ", mSign='" + mSign + '\'' +
                '}';
    }
}
