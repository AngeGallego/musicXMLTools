package MusicXMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicXMLParser {

    private MusicXMLFileBuilder mFileBuilder;

    public MusicXMLFile parse(String fileName) throws IOException, ParserConfigurationException, SAXException {
        Document doc = createDocument(fileName);
        mFileBuilder = new MusicXMLFileBuilder();
        preprocessFile(doc);
        parseFile(doc);
        return mFileBuilder.getOutput();
    }

    private void preprocessFile(Document doc) {
        // in case we need to preprocess the file (associating part names with part ids, ...)
    }

    /**
     * Iterating through each part of the file
     * @param doc musicXML document
     */
    private void parseFile(Document doc) {
        NodeList parts = doc.getElementsByTagName("part");

        for (int i = 0; i < parts.getLength(); i++) {
            Node xmlPart = parts.item(i);
            mFileBuilder.pushPart(xmlPart);
            parsePart(xmlPart);
        }
    }

    /**
     * Parsing of one part
     * @param xmlPart one part of musicXML file
     */
    private void parsePart(Node xmlPart) {

        if (xmlPart.getNodeType() == Node.ELEMENT_NODE) {
            NodeList measures = ((Element) xmlPart).getElementsByTagName("measure");

            for (int i = 0; i < measures.getLength(); i++) {
                Node xmlMeasure = measures.item(i);
                mFileBuilder.pushMeasure(xmlMeasure);
                parseMeasure(xmlMeasure);
            }
        }
    }

    /**
     * Parsing of one measure
     * @param xmlMeasure one measure of musicXML file
     */
    private void parseMeasure(Node xmlMeasure) {

        if (xmlMeasure.getNodeType() == Node.ELEMENT_NODE) {
            parseAttributes((Element) xmlMeasure);
            parseDynamics((Element) xmlMeasure);
            NodeList notes = ((Element) xmlMeasure).getElementsByTagName("note");

            for (int i = 0; i < notes.getLength(); i++) {
                Node xmlNote = notes.item(i);
                if (xmlNote.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) xmlNote;
                    mFileBuilder.pushElement(element);
                }
            }
            mFileBuilder.flushChords();
        }
    }

    private void parseDynamics(Element xmlMeasure) {
        NodeList attributes = xmlMeasure.getElementsByTagName("dynamics");

        for (int i = 0; i < attributes.getLength(); i++) {
            Node xmlAttribute = attributes.item(i);
            if (xmlAttribute.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) xmlAttribute;
                mFileBuilder.pushDynamics(element);
            }
        }
    }

    private void parseAttributes(Element xmlMeasure) {
        NodeList attributes = xmlMeasure.getElementsByTagName("attributes");

        for (int i = 0; i < attributes.getLength(); i++) {
            Node xmlAttribute = attributes.item(i);
            if (xmlAttribute.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) xmlAttribute;
                mFileBuilder.pushAttributes(element);
            }
        }
    }

    /**
     * Opens the specified file and creates an XML document object from it which
     * can then be parsed/traversed.
     * @param fileName the file to parse
     * @return the XML document corresponding to the specified XML file
     */
    private Document createDocument(String fileName) throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = new FileInputStream(fileName);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();
        return doc;
    }
}
