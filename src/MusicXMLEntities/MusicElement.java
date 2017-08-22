package MusicXMLEntities;

import MusicXMLDiff.ComparisonResult;

import java.util.ArrayList;

public abstract class MusicElement {

    MeasureAttributes mAttrs;

    public void setAttrs(MeasureAttributes attrs) {
        mAttrs = attrs;
    }

    public abstract boolean equals(Object obj);
    public abstract String toString();
    public abstract int compareTo(MusicElement element);
    public abstract ArrayList<ComparisonResult> inDepthComparison(MusicElement element);
}
