package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Iterator;

public class Measure extends MusicElement implements Iterable<MusicElement> {

    private MeasureAttributes mAttributes;
    private ArrayList<MusicElement> mElements = new ArrayList<>();
    private String mNumber;

    public Measure(Node xmlMeasure) {
        mNumber = xmlMeasure.getAttributes().getNamedItem("number").getTextContent();
    }

    public void pushElement(MusicElement element) {
        mElements.add(element);
    }
    public void setAttributes(MeasureAttributes attributes) {
        mAttributes = attributes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Measure.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Measure measure = (Measure) obj;
        return true;
        //return measure.mNumber.equals(mNumber);
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
    public Iterator<MusicElement> iterator() {
        return mElements.iterator();
    }

    @Override
    public String toString() {
        return "Measure{" +
                "mElements=" + mElements +
                ", mNumber='" + mNumber + '\'' +
                '}';
    }
}
