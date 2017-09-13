package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Note extends MusicElement {

    private Pitch  mPitch = new Pitch();
    private String mDuration = "";
    private String mType = "";

    public Note(Element xmlNote) {
        NodeList components = XMLUtils.getComponents(xmlNote, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "duration":
                    mDuration = current.getTextContent();
                    break;
                case "type":
                    mType = current.getTextContent();
                    break;
                case "pitch":
                    mPitch = new Pitch((Element) current);
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
        if (!Note.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Note note = (Note) obj;
        if (mAttrs.division().equals(note.mAttrs.division()))
            return note.mPitch.equals(mPitch) && note.mDuration.equals(mDuration) && note.mType.equals(mType);
        else
            return note.mPitch.equals(mPitch) && note.mType.equals(mType);
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
        if (!Note.class.isAssignableFrom(element.getClass()))
            return results;
        final Note note = (Note) element;
        if (!mPitch.equals(note.mPitch))
            results.add(ComparisonResult.HARMONIC);
        if (!mDuration.equals(note.mDuration))
            results.add(ComparisonResult.RYTHMIC);
        if (!mType.equals(note.mType))
            results.add(ComparisonResult.TYPE);
        return results;
    }

    @Override
    public String toString() {
        return "Note{" +
                "mPitch=" + mPitch +
                ", mDuration='" + mDuration + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}
