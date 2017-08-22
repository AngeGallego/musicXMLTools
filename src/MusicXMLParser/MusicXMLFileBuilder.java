package MusicXMLParser;

import MusicXMLEntities.*;
import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class MusicXMLFileBuilder {

    private MusicXMLFile mFile;
    private Part mCurrentPart;
    private Measure mCurrentMeasure;
    private MeasureAttributes mCurrentMeasureAttrs;
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
            mCurrentMeasureAttrs = attrs;
            mCurrentMeasure.pushElement(attrs);
            mFile.pushElement(attrs);
        }
    }

    void pushDynamics(Element dynamics) {
        if (dynamics != null) {
            mIsChordInConstruction = false;
            Dynamics dyna = new Dynamics(dynamics);
            mCurrentMeasure.pushElement(dyna);
            mFile.pushElement(dyna);
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
            mCurrentChord.setAttrs(mCurrentMeasureAttrs);
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
        Note note = new Note(chordNote);
        note.setAttrs(mCurrentMeasureAttrs);
        mCurrentChord.pushNote(note);
    }

    private void pushNote(Element elemNote) {
        mIsChordInConstruction = false;
        tryPushChord();
        Note note = new Note(elemNote);
        note.setAttrs(mCurrentMeasureAttrs);
        mCurrentMeasure.pushElement(note);
        mFile.pushElement(note);
    }

    private void pushRest(Element elemRest) {
        mIsChordInConstruction = false;
        tryPushChord();
        Rest rest = new Rest(elemRest);
        rest.setAttrs(mCurrentMeasureAttrs);
        mCurrentMeasure.pushElement(rest);
        mFile.pushElement(rest);
    }

    void flushChords() {
        if (mCurrentChord != null) {
            mCurrentChord.setAttrs(mCurrentMeasureAttrs);
            mCurrentMeasure.pushElement(mCurrentChord);
            mFile.pushElement(mCurrentChord);
            mCurrentChord = null;
        }
    }
}
