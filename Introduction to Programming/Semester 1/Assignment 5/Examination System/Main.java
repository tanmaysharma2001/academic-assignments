/* Tanmay Sharma - Assignment 5 - Examination System. */
/* t.sharma@innopolis.university */
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        try {
            List<Question> questions = parser.readInput();
            Question.reorderQuestions(questions);
            String prePrint = "==============Exam==============\n";
            prePrint += Question.print(questions);
            prePrint += "==========Exam answers==========\n";
            prePrint += Question.printWithAnswers(questions);
            output(prePrint);
        } catch (NullPointerException e) {
            output("Wrong formatted input file");
        }
    }

    public static void output(String text) {
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Exception: Couldn't output!");
        }
    }
}