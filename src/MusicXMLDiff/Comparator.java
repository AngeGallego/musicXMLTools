package MusicXMLDiff;

import MusicXMLParser.MusicXMLFile;

public abstract class Comparator {

    public static int MAX_SUBSTITUTION_COST = 3; // can't be 0
    MusicXMLFile mGroundTruth;

    Comparator(MusicXMLFile groundTruth) {
        mGroundTruth = groundTruth;
    }

    public abstract float compare(MusicXMLFile evaluated);
}
