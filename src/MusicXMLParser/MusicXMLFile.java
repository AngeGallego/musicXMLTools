package MusicXMLParser;

        import MusicXMLEntities.MusicElement;
        import MusicXMLEntities.Part;
        import java.util.ArrayList;

public class MusicXMLFile implements MusicXMLFileIterable {

    private ArrayList<MusicElement> mElements = new ArrayList<>();
    private ArrayList<Part> mParts = new ArrayList<>();

    void pushPart(Part part) {
        mParts.add(part);
    }

    void pushElement(MusicElement element) {
        mElements.add(element);
    }

    public int length() {
        return mElements.size();
    }

    public MusicElement getElement(int index) {
        return mElements.get(index);
    }

    @Override
    public Iterable<Part> partIterator() {
        return mParts;
    }

    @Override
    public Iterable<MusicElement> elementsIterator() {
        return mElements;
    }
}
