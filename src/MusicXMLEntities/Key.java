package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Key extends MusicElement {

    private String mMode = "";
    private String mFifth = "";

    public Key(Element xmlKey) {
        NodeList components = XMLUtils.getComponents(xmlKey, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "fifths":
                    mFifth = current.getTextContent();
                    break;
                case "mode":
                    mMode = current.getTextContent();
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
        if (!Key.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Key key = (Key) obj;
        return key.mFifth.equals(mFifth) &&
                key.mMode.equals(mMode);
    }

    @Override
    public int compareTo(MusicElement element) {
        return equals(element) ? 0 : Comparator.MAX_SUBSTITUTION_COST;
    }

    @Override
    public ArrayList<ComparisonResult> inDepthComparison(MusicElement element) {
        return null;
    }

    @Override
    public String toString() {
        return "Key{" +
                "mMode='" + mMode + '\'' +
                ", mFifth='" + mFifth + '\'' +
                '}';
    }
}
