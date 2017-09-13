package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Time extends MusicElement {

    private String mBeats = "";
    private String mBeatType = "";

    public Time(Element xmlKey) {
        NodeList components = XMLUtils.getComponents(xmlKey, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "beats":
                    mBeats = current.getTextContent();
                    break;
                case "beat-type":
                    mBeatType = current.getTextContent();
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
        if (!Time.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Time time = (Time) obj;
        return time.mBeatType.equals(mBeatType) && time.mBeats.equals(mBeats);
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
        return "Time{" +
                "mBeats='" + mBeats + '\'' +
                ", mBeatType='" + mBeatType + '\'' +
                '}';
    }
}
