package MusicXMLEntities;

import MusicXMLDiff.Comparator;
import MusicXMLDiff.ComparisonResult;

import java.util.ArrayList;
import java.util.Iterator;

public class Chord extends MusicElement implements Iterable<Note> {

    private ArrayList<Note> mNotes = new ArrayList<>();

    public void pushNote(Note note) {
        mNotes.add(note);
    }

    private int length() {
        return mNotes.size();
    }

    @Override
    public Iterator<Note> iterator() {
        return mNotes.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Chord.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Chord chord = (Chord) obj;
        if (chord.length() != length())
            return false;
        for (Note note : chord)
            if (!mNotes.contains(note))
                return false;
        return true;
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
        return "Chord{" +
                "mNotes=" + mNotes +
                '}';
    }
}
