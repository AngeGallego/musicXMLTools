package MusicXMLDiff;

import MusicXMLParser.MusicXMLFile;

public abstract class Comparator {

    static int MAX_SUBSTITUTION_COST = 3;
    MusicXMLFile mGroundTruth;

    Comparator(MusicXMLFile groundTruth) {
        mGroundTruth = groundTruth;
    }

    public abstract float compare(MusicXMLFile evaluated);
}
