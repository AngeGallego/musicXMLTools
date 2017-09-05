package MusicXMLDiff;

import MusicXMLEntities.*;

import java.util.ArrayList;
import java.util.Stack;

public class ErrorClassifier {

    private Stack<ArrayList<ComparisonResult>> mErrorsStack = new Stack<>();
    private DiffLogger mLogger = null;

    public ErrorClassifier(DiffLogger logger) {
        mLogger = logger;
    }

    void compareElements(MusicElement ground, MusicElement toCompare) {
        ArrayList<ComparisonResult> results = ground.inDepthComparison(toCompare);
        if (results.size() > 0) {
            ComparisonResult last = results.get(results.size() - 1);
            last.enrichLog(toCompare);
            mErrorsStack.push(results);
        }
    }

    public void report() {
        if (mErrorsStack.size() > 0) {
            while (!mErrorsStack.isEmpty()) {
                ArrayList<ComparisonResult> results = mErrorsStack.pop();
                for (ComparisonResult result : results)
                    mLogger.logComparisonResult(result);
            }
        }
    }

    public String summary(float editDistance) {
        int dynamicCount = 0,
                rythmicCount = 0,
                harmonicCount = 0,
                typeCount = 0,
                globalCount = 0;
        for(ArrayList<ComparisonResult> list : mErrorsStack) {
            for (ComparisonResult result : list) {
                int type = result.type();
                switch (type) {
                    case 0:
                        globalCount += 1;
                        break;
                    case 1:
                        globalCount += 1;
                        break;
                    case 2:
                        rythmicCount += 1;
                        break;
                    case 3:
                        harmonicCount += 1;
                        break;
                    case 4:
                        typeCount += 1;
                        break;
                    case 5:
                        dynamicCount += 1;
                        break;
                }
            }
        }
        return "{harmonic_score:" + harmonicCount +
                ",rythmic_score:" + rythmicCount +
                ",type_score:" + typeCount +
                ",dynamic_score:" + dynamicCount +
                ",global_score:" + globalCount +
                ",edit_distance:" + editDistance +
                "}";
    }
}
