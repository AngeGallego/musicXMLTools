package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
        return rest.mType.equals(mType) && rest.mDuration.equals(mDuration);
    }

    @Override
    public int compareTo(MusicElement element) {
        return equals(element) ? 0 : Comparator.MAX_SUBSTITUTION_COST;
    }

    @Override
    public String toString() {
        return "Rest{" +
                "mDuration='" + mDuration + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}
