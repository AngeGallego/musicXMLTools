package MusicXMLEntities;

import XMLUtils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class Pitch {

    private String mStep = "";
    private String mAlter = "";
    private String mOctave = "";

    Pitch() {}

    Pitch(Element xmlPitch) {
        NodeList components = XMLUtils.getComponents(xmlPitch, "*");

        for (int i = 0; i < components.getLength(); i++) {
            Node current = components.item(i);
            switch (current.getNodeName()) {
                case "step":
                    mStep = current.getTextContent();
                    break;
                case "alter":
                    mAlter = current.getTextContent();
                    break;
                case "octave":
                    mOctave = current.getTextContent();
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
        if (!Pitch.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Pitch pitch = (Pitch) obj;
        return pitch.mOctave.equals(mOctave)
                && pitch.mStep.equals(mStep);
                //&& pitch.mAlter.equals(mAlter);
        /*
          Alter are ignored at the moment, to avoid global mistakes to be detected multiple times
          To compare local alteration we uses the <accidental> tag in a note
         */
    }

    @Override
    public String toString() {
        return "Pitch{" +
                "mStep='" + mStep + '\'' +
                ", mAlter='" + mAlter + '\'' +
                ", mOctave='" + mOctave + '\'' +
                '}';
    }
}
