package MusicXMLDiff;

import MusicXMLParser.MusicXMLFile;

import java.util.ArrayList;

public class LevenshteinComparator extends Comparator {

    private boolean mPrintLogs = false;
    private ArrayList<String> mErrorLog;

    public LevenshteinComparator(MusicXMLFile groundTruth) {
        super(groundTruth);
    }

    public LevenshteinComparator(MusicXMLFile groundTruth, boolean printLogs) {
        super(groundTruth);
        mPrintLogs = printLogs;
    }

    public void setLogging(boolean loggingEnabled) {
        mPrintLogs = loggingEnabled;
    }

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
                        distanceMatrix[i - 1][j] + MAX_SUBSTITUTION_COST, // could be +5 with substitutionCost varying from 0 to 5
                        distanceMatrix[i][j - 1] + MAX_SUBSTITUTION_COST,
                        distanceMatrix[i - 1][j - 1] + substitutionCost
                );
            }
        }
        if (mPrintLogs) {
            mErrorLog = new ArrayList<>();
            createBackTrace(distanceMatrix, evaluated, mGroundTruth.length(), evaluated.length());
            printBackTrace();
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
                mErrorLog.add("Substitution of " + mGroundTruth.getElement(x - 1) + " with " + evaluated.getElement(y - 1));
                createBackTrace(distanceMatrix, evaluated, x - 1, y - 1);
            } else if (distanceMatrix[x - 1][y] == minimum) {
                mErrorLog.add("Deletion of " + mGroundTruth.getElement(x - 1));
                createBackTrace(distanceMatrix, evaluated, x - 1, y);
            } else if (distanceMatrix[x][y - 1] == minimum) {
                mErrorLog.add("Addition of " + evaluated.getElement(y - 1));
                createBackTrace(distanceMatrix, evaluated, x, y - 1);
            }
        }
        else
            createBackTrace(distanceMatrix, evaluated, x - 1, y - 1);
    }

    private void printBackTrace() {
        for (int i = mErrorLog.size() - 1; i >= 0; --i) {
            System.out.println(mErrorLog.get(i));
        }
    }

    private int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }
    private float minimum(float a, float b, float c) {
        return Math.min(Math.min(a, b), c);
    }
}
