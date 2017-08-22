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
}
