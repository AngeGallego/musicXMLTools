package MusicXMLParser;

import MusicXMLEntities.MusicElement;
import MusicXMLEntities.Part;

public interface MusicXMLFileIterable {
    Iterable<Part> partIterator();
    Iterable<MusicElement> elementsIterator();
}
