
import MusicXMLDiff.EditDistance;
import MusicXMLEntities.Measure;
import MusicXMLEntities.MusicElement;
import MusicXMLEntities.Part;
import MusicXMLParser.MusicXMLFile;
import MusicXMLParser.MusicXMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String argv[]) {
        try {

            /*
             ** Creating a parser
              */
            MusicXMLParser parser = new MusicXMLParser();

            /*
             ** Parsing all the files
              */
            MusicXMLFile tinyFile = parser.parse("/home/galleg_a/musicXMLTools/src/XMLSamples/tinyMusic.xml");
            MusicXMLFile tinyFile2 = parser.parse("/home/galleg_a/musicXMLTools/src/XMLSamples/tinyMusic2.xml");
            MusicXMLFile file = parser.parse("/home/galleg_a/musicXMLTools/src/XMLSamples/music.xml");
            MusicXMLFile file2 = parser.parse("/home/galleg_a/musicXMLTools/src/XMLSamples/music2.xml");
            MusicXMLFile bigFile = parser.parse("/home/galleg_a/musicXMLTools/src/XMLSamples/bigMusic.xml");
            MusicXMLFile bigFile2 = parser.parse("/home/galleg_a/musicXMLTools/src/XMLSamples/bigMusic2.xml");

            /*
             ** Displaying a file
              */
            displayFileElements(tinyFile);

            /*
             ** Computing edit distance of files
              */
            System.out.println("Edit Distance tiny : " + EditDistance.editDistance(tinyFile, tinyFile2));
            System.out.println("Edit Distance medium : " + EditDistance.editDistance(file, file2));
            System.out.println("Edit Distance big : " + EditDistance.editDistance(bigFile, bigFile2));
        } catch (SAXException |
                 ParserConfigurationException |
                 IOException e) {
            e.printStackTrace();
        }
    }

    /*
     ** First way of iterating through file
      */
    private static void displayFileParts(MusicXMLFile file) {
        for (Part part : file.partIterator())
            for (Measure measure : part)
                for (MusicElement element : measure)
                    System.out.println(element);
    }

    /*
     ** Second way of iterating through file
      */
    private static void displayFileElements(MusicXMLFile file) {
        for (MusicElement element : file.elementsIterator())
            System.out.println(element);
    }
}