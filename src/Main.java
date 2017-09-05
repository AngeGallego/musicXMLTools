
import MusicXMLDiff.Comparator;
import MusicXMLDiff.LevenshteinComparator;
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
/*
            MusicXMLFile audiveris1 = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/ground-lg-302588546126612838.xml");
            MusicXMLFile audiveris2 = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/compared-lg-302588546126612838.xml");
            MusicXMLFile audiveris3 = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/2ndcompared-lg-302588546126612838.xml");
*/
            MusicXMLFile tinyFile = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/tinyMusic.xml");
            MusicXMLFile tinyFile2 = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/tinyMusic2.xml");
            MusicXMLFile file = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/music.xml");
            MusicXMLFile file2 = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/music2.xml");
            MusicXMLFile bigFile = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/bigMusic.xml");
            MusicXMLFile bigFile2 = parser.parse("/home/galleg_a/audiveris_mirrored/musicXMLTools/src/XMLSamples/bigMusic2.xml");

            /*
             ** Displaying a file
              */
            System.out.println("\nDisplaying tiny file :");
            displayFileElements(tinyFile);

            /*
             ** Computing edit distance of files with or without backtrace
              */
            Comparator tiny = new LevenshteinComparator(tinyFile, true);
            Comparator medium = new LevenshteinComparator(file, false);
            Comparator big = new LevenshteinComparator(bigFile, true);
            //Comparator audiveris = new LevenshteinComparator(audiveris1, false);
/*
            System.out.println("\nEdition script for tiny file :");
            System.out.println("Edit Distance tiny : " + audiveris.compare(audiveris2));
            System.out.println("\nEdition script for tiny file :");
            System.out.println("Edit Distance tiny : " + audiveris.compare(audiveris3));
            System.out.println("nb of symbols : " + audiveris3.length());
*/

            System.out.println("\nEdition script for tiny file :");
            System.out.println("Edit Distance tiny : " + tiny.compare(tinyFile2));
            System.out.println("\nEdition script for medium file :");
            System.out.println("Edit Distance medium : " + medium.compare(file2));
            System.out.println("Edit Distance big : " + big.compare(bigFile2));

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