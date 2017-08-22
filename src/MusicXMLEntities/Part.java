package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Iterator;

public class Part extends MusicElement implements Iterable<Measure> {

    private ArrayList<Measure> mMeasures = new ArrayList<>();
    private String mId;

    public Part(Node xmlPart) {
        mId = xmlPart.getAttributes().getNamedItem("id").getTextContent();
    }

    public void pushMeasure(Measure measure) {
        mMeasures.add(measure);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Part.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Part part = (Part) obj;
        return part.mId.equals(mId);
    }

    @Override
    public Iterator<Measure> iterator() {
        return mMeasures.iterator();
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
        return "Part{" +
                "mMeasures=" + mMeasures +
                ", mId='" + mId + '\'' +
                '}';
    }
}
