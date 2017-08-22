package MusicXMLDiff;

import MusicXMLEntities.*;
import MusicXMLParser.MusicXMLFile;

public class LevenshteinComparator extends Comparator {

    private boolean mPrintLogs = false;
    private DiffLogger mLogger = new DiffLogger();
    private ErrorClassifier mErrorClassifier;

    public LevenshteinComparator(MusicXMLFile groundTruth) {
        super(groundTruth);
        mErrorClassifier = new ErrorClassifier(mLogger);
    }

    public LevenshteinComparator(MusicXMLFile groundTruth, boolean printLogs) {
        super(groundTruth);
        mPrintLogs = printLogs;
        mErrorClassifier = new ErrorClassifier(mLogger);
    }

    public void setLogging(boolean loggingEnabled) {
        mPrintLogs = loggingEnabled;
    }

    //Todo: make a new ComparisonResult class which is more flexible and complete (handle multiple errors, additional log, ...)

    public float compare(MusicXMLFile evaluated) {
        float[][] distanceMatrix = new float[mGroundTruth.length() + 1][evaluated.length() + 1];

        for (int i = 0; i <= mGroundTruth.length(); i++)
            distanceMatrix[i][0] = i;
        for (int i = 0; i <= evaluated.length(); i++)
            distanceMatrix[0][i] = i;

        for (int i = 1; i <= mGroundTruth.length(); i++) {
            int substitutionCost;
            for (int j = 1; j <= evaluated.length(); j++) {
                substitutionCost = mGroundTruth.getElement(i - 1).compareTo(evaluated.getElement(j - 1));
                distanceMatrix[i][j] = minimum(
                        distanceMatrix[i - 1][j] + MAX_SUBSTITUTION_COST,
                        distanceMatrix[i][j - 1] + MAX_SUBSTITUTION_COST,
                        distanceMatrix[i - 1][j - 1] + substitutionCost
                );
            }
        }
        if (mPrintLogs) {
            createBackTrace(distanceMatrix, evaluated, mGroundTruth.length(), evaluated.length());
            printBackTrace();
            mErrorClassifier.report();
        }
        return distanceMatrix[mGroundTruth.length()][evaluated.length()] / MAX_SUBSTITUTION_COST;
    }

    private void createBackTrace(float[][] distanceMatrix, MusicXMLFile evaluated, int x, int y) {
        if (x == 0 && y == 0)
            return ;
        float current = distanceMatrix[x][y];
        float minimum = minimum(distanceMatrix[x - 1][y - 1], distanceMatrix[x - 1][y], distanceMatrix[x][y - 1]);
        if (minimum < current) {
            if (distanceMatrix[x - 1][y - 1] == minimum) {
                mLogger.stackMessage("Substitution of " + mGroundTruth.getElement(x - 1) + " with " + evaluated.getElement(y - 1));
                createBackTrace(distanceMatrix, evaluated, x - 1, y - 1);
                mErrorClassifier.compareElements(mGroundTruth.getElement(x - 1), evaluated.getElement(y - 1));
            } else if (distanceMatrix[x - 1][y] == minimum) {
                mLogger.stackMessage("Deletion of " + mGroundTruth.getElement(x - 1));
                createBackTrace(distanceMatrix, evaluated, x - 1, y);
            } else if (distanceMatrix[x][y - 1] == minimum) {
                mLogger.stackMessage("Addition of " + evaluated.getElement(y - 1));
                createBackTrace(distanceMatrix, evaluated, x, y - 1);
            }
        }
        else
            createBackTrace(distanceMatrix, evaluated, x - 1, y - 1);
    }

    private void printBackTrace() {
        mLogger.popAllMessages();
    }

    private int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }
    private float minimum(float a, float b, float c) {
        return Math.min(Math.min(a, b), c);
    }
}
