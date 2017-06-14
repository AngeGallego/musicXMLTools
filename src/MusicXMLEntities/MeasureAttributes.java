package MusicXMLEntities;

import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MeasureAttributes extends MusicElement {

    private String mDivision = "";
    private String mKey = "";
    private String mTime = "";
    private String mClef = "";

    public MeasureAttributes(Element xmlMeasureAttributes) {
        NodeList components = XMLUtils.getComponents(xmlMeasureAttributes, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "divisions":
                    mDivision = current.getTextContent();
                    break;
                case "key":
                    mKey = current.getTextContent();
                    break;
                case "time":
                    mTime = current.getTextContent();
                    break;
                case "clef":
                    mClef = current.getTextContent();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!MeasureAttributes.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final MeasureAttributes attributes = (MeasureAttributes) obj;
        return false;
    }

    @Override
    public String toString() {
        return "MeasureAttributes{" +
                "mDivision='" + mDivision + '\'' +
                ", mKey='" + mKey + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mClef='" + mClef + '\'' +
                '}';
    }
}
