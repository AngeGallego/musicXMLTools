package MusicXMLDiff;

import MusicXMLEntities.MusicElement;

public enum ComparisonResult {

    GLOBAL_RYTHMIC("global rythmic error", 0),
    GLOBAL_HARMONIC("global harmonic error", 1),
    RYTHMIC("rythmic error", 2),
    HARMONIC("harmonic error", 3),
    TYPE("type error", 4),
    DYNAMIC("dynamic error", 5);

    private int mErrorType = 0;
    private boolean mIsGlobal = false;
    private String mComparisonLog = "";

    ComparisonResult(String error, int type) {
        mComparisonLog = error;
        mErrorType = type;
    }

    public void enrichLog(MusicElement element) {
        mComparisonLog += "\r\nat " + element.toString();
    }

    public boolean isGlobal() {
        return mIsGlobal;
    }
    public int type() {
        return mErrorType;
    }
    public String getMessage() {
        return mComparisonLog;
    }
}
