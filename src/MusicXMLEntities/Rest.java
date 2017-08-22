package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Rest extends MusicElement {

    private String mDuration = "";
    private String mType = "";

    public Rest(Element xmlRest) {
        NodeList components = XMLUtils.getComponents(xmlRest, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "duration":
                    mDuration = current.getTextContent();
                    break;
                case "type":
                    mType = current.getTextContent();
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
        if (!Rest.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Rest rest = (Rest) obj;
        if (mAttrs.division().equals(rest.mAttrs.division()))
            return rest.mType.equals(mType) && rest.mDuration.equals(mDuration);
        else
            return rest.mType.equals(mType);
    }

    @Override
    public int compareTo(MusicElement element) {
        return equals(element) ? 0 : Comparator.MAX_SUBSTITUTION_COST;
    }

    @Override
    public ArrayList<ComparisonResult> inDepthComparison(MusicElement element) {
        if (element == null)
            return null;
        if (!Rest.class.isAssignableFrom(element.getClass()))
            return null;
        ArrayList<ComparisonResult> results = new ArrayList<>();
        final Rest rest = (Rest) element;
        if (!mDuration.equals(rest.mDuration))
            results.add(ComparisonResult.RYTHMIC);
        if (!mType.equals(rest.mType))
            results.add(ComparisonResult.TYPE);
        return results;
    }

    @Override
    public String toString() {
        return "Rest{" +
                "mDuration='" + mDuration + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}
