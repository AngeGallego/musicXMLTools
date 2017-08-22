package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import MusicXMLDiff.ComparisonUtils;
import XMLUtils.XMLUtils;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class MeasureAttributes extends MusicElement {

    private String mDivision = "";
    private Key mKey;
    private Time mTime;
    private Clef mClef;
    private Transpose mTranspose;

    public MeasureAttributes(Element xmlMeasureAttributes) {
        NodeList components = XMLUtils.getComponents(xmlMeasureAttributes, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "divisions":
                    mDivision = current.getTextContent();
                    break;
                case "key":
                    mKey = new Key((Element) current);
                    break;
                case "time":
                    mTime = new Time((Element) current);
                    break;
                case "clef":
                    mClef = new Clef((Element) current);
                    break;
                case "transpose":
                    mTranspose = new Transpose((Element) current);
                    break;
                default:
                    break;
            }
        }
    }

    String division() {
        return mDivision;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!MeasureAttributes.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final MeasureAttributes attrs = (MeasureAttributes) obj;
        return attrs.mDivision.equals(mDivision) &&
                ComparisonUtils.compareHandleNull(attrs.mClef, mClef) &&
                ComparisonUtils.compareHandleNull(attrs.mTime, mTime) &&
                ComparisonUtils.compareHandleNull(attrs.mTranspose, mTranspose) &&
                ComparisonUtils.compareHandleNull(attrs.mKey, mKey);
    }

    @Override
    public int compareTo(MusicElement element) {
        return equals(element) ? 0 : Comparator.MAX_SUBSTITUTION_COST;
    }

    @Override
    public ArrayList<ComparisonResult> inDepthComparison(MusicElement element) {
        if (element == null)
            return null;
        if (!MeasureAttributes.class.isAssignableFrom(element.getClass()))
            return null;
        final MeasureAttributes measureAttributes = (MeasureAttributes) element;
        ArrayList<ComparisonResult> results = new ArrayList<>();
        if (!mDivision.equals(measureAttributes.mDivision))
            results.add(ComparisonResult.GLOBAL_RYTHMIC);
        if (!mKey.equals(measureAttributes.mKey))
            results.add(ComparisonResult.GLOBAL_HARMONIC);
        return results;
    }

    @Override
    public String toString() {
        return "MeasureAttributes{" +
                "mDivision='" + mDivision + '\'' +
                ", mKey=" + mKey +
                ", mTime=" + mTime +
                ", mClef=" + mClef +
                ", mTranspose=" + mTranspose +
                '}';
    }
}
