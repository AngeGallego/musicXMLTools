package MusicXMLDiff;

import MusicXMLParser.MusicXMLFile;

public class EditDistance {

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int editDistance(MusicXMLFile a, MusicXMLFile b) {
        int[][] distanceMatrix = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++)
            distanceMatrix[i][0] = i;
        for (int i = 0; i <= b.length(); i++)
            distanceMatrix[0][i] = i;

        for (int i = 1; i <= a.length(); i++) {
            int substitutionCost;
            for (int j = 1; j <= b.length(); j++) {
                if (b.getElement(j - 1).equals(a.getElement(i - 1))) // instead of perfect equality, compute "matching score" and use it as substitutionCost
                    substitutionCost = 0;
                else
                    substitutionCost = 1;
                distanceMatrix[i][j] = minimum(
                        distanceMatrix[i - 1][j] + 1, // could be +5 with substitutionCost varying from 0 to 5
                        distanceMatrix[i][j - 1] + 1,
                        distanceMatrix[i - 1][j - 1] + substitutionCost
                );
            }
        }
        return distanceMatrix[a.length()][b.length()];
    }
}
