package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Transpose extends MusicElement {

    private String mDiatonic = "";
    private String mChromatic = "";

    public Transpose(Element xmlKey) {
        NodeList components = XMLUtils.getComponents(xmlKey, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "diatonic":
                    mDiatonic = current.getTextContent();
                    break;
                case "chromatic":
                    mChromatic = current.getTextContent();
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
        if (!Transpose.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Transpose transpose = (Transpose) obj;
        return transpose.mChromatic.equals(mChromatic) && transpose.mDiatonic.equals(mDiatonic);
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
        return "Transpose{" +
                "mDiatonic='" + mDiatonic + '\'' +
                ", mChromatic='" + mChromatic + '\'' +
                '}';
    }
}
