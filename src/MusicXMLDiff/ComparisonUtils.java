package MusicXMLDiff;

public class ComparisonUtils {

    public static boolean compareHandleNull(Object a, Object b) {
        if (a == null && b == null)
            return true;
        return !(a == null || b == null) && a.equals(b);
    }

}
