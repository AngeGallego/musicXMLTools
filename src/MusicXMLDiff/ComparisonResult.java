package MusicXMLDiff;

import MusicXMLEntities.MusicElement;

public enum ComparisonResult {

    GLOBAL_RYTHMIC("global rythmic error", true),
    GLOBAL_HARMONIC("global harmonic error", true),
    RYTHMIC("rythmic error", false),
    HARMONIC("harmonic error", false),
    TYPE("type error", false),
    DYNAMIC("dynamic error", false);

    private boolean mIsGlobal = false;
    private String mComparisonLog = "";

    ComparisonResult(String error, boolean isGlobal) {
        mComparisonLog = error;
        mIsGlobal = isGlobal;
    }

    public void enrichLog(MusicElement element) {
        mComparisonLog += "\r\nat " + element.toString();
    }

    public boolean isGlobal() {
        return mIsGlobal;
    }

    public String getMessage() {
        return mComparisonLog;
    }
}
