package XMLUtils;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLUtils {

    /**
     * Checks whether the XML Element has the specified attribute
     * @param element the XML Element to look inside
     * @param component name of the component we look for
     */
    public static boolean hasComponent(Element element, final String component) {
        NodeList elements = element.getElementsByTagName(component);
        Element requiredElement = (Element) elements.item(0);
        return requiredElement != null;
    }

    /**
     * returns the specified component
     * @param element the XML Element to look inside
     * @param component name of the component we look for
     */
    public static NodeList getComponents(Element element, final String component) {
        return element.getElementsByTagName(component);
    }

}
