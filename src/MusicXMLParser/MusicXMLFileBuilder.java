package MusicXMLParser;

import MusicXMLEntities.*;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class MusicXMLFileBuilder {

    private MusicXMLFile mFile;
    private Part mCurrentPart;
    private Measure mCurrentMeasure;
    private Chord mCurrentChord;
    private boolean mIsChordInConstruction = false;

    MusicXMLFileBuilder() {
        mFile = new MusicXMLFile();
    }

    MusicXMLFile getOutput() {
        return mFile;
    }

    void pushPart(Node xmlPart) {
        if (xmlPart != null) {
            Part part = new Part(xmlPart);
            mFile.pushPart(part);
            mFile.pushElement(part);
            mCurrentPart = part;
        }
    }

    void pushMeasure(Node xmlMeasure) {
        if (xmlMeasure != null) {
            Measure measure = new Measure(xmlMeasure);
            mFile.pushElement(measure);
            mCurrentPart.pushMeasure(measure);
            mCurrentMeasure = measure;
        }
    }

    void pushAttributes(Element attributes) {
        if (attributes != null) {
            mIsChordInConstruction = false;
            MeasureAttributes attrs = new MeasureAttributes(attributes);
            mCurrentMeasure.pushElement(attrs);
            mFile.pushElement(attrs);
        }
    }

    void pushElement(Element note) {
        if (note != null) {
            if (XMLUtils.hasComponent(note, "rest"))
                pushRest(note);
            else if (XMLUtils.hasComponent(note, "chord"))
                pushChordNote(note);
            else
                pushNote(note);
        }
    }

    private void tryPushChord() {
        if (!mIsChordInConstruction && mCurrentChord != null) {
            mCurrentMeasure.pushElement(mCurrentChord);
            mFile.pushElement(mCurrentChord);
            mCurrentChord = null;
        }
    }

    private void pushChordNote(Element chordNote) {
        if (mCurrentChord == null) {
            mCurrentChord = new Chord();
            mIsChordInConstruction = true;
        }
        mCurrentChord.pushNote(new Note(chordNote));
    }

    private void pushNote(Element elemNote) {
        mIsChordInConstruction = false;
        tryPushChord();
        Note note = new Note(elemNote);
        mCurrentMeasure.pushElement(note);
        mFile.pushElement(note);
    }

    private void pushRest(Element elemRest) {
        mIsChordInConstruction = false;
        tryPushChord();
        Rest rest = new Rest(elemRest);
        mCurrentMeasure.pushElement(rest);
        mFile.pushElement(rest);
    }

    void flushChords() {
        if (mCurrentChord != null) {
            mCurrentMeasure.pushElement(mCurrentChord);
            mFile.pushElement(mCurrentChord);
            mCurrentChord = null;
        }
    }
}
