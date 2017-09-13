package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Dynamics extends MusicElement {

    private String mType = "";

    public Dynamics(Element xmlKey) {
        NodeList components = XMLUtils.getComponents(xmlKey, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            mType = current.getNodeName();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Dynamics.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Dynamics dynamics = (Dynamics) obj;
        return mType.equals(dynamics.mType);
    }

    @Override
    public int compareTo(MusicElement element) {
        return equals(element) ? 0 : Comparator.MAX_SUBSTITUTION_COST;
    }

    @Override
    public ArrayList<ComparisonResult> inDepthComparison(MusicElement element) {
        ArrayList<ComparisonResult> results = new ArrayList<>();
        if (element == null)
            return results;
        if (!Dynamics.class.isAssignableFrom(element.getClass()))
            return results;
        final Dynamics dyn = (Dynamics) element;
        if (!mType.equals(dyn.mType))
            results.add(ComparisonResult.DYNAMIC);
        return results;
    }

    @Override
    public String toString() {
        return "Dynamics{" +
                "mName='" + mType + '\'' +
                '}';
    }
}
