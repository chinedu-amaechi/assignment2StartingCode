package appDomain;

import implementations.MyStack;
import implementations.MyQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * XMLParser for validating XML tags and detecting errors.
 *
 * Uses a stack for managing opening tags and a queue for storing error messages.
 * Handles self-closing tags correctly.
 *
 * @author chyme
 */
public class XMLParser {
    private MyStack<String> tagStack;     // Stack to track opening tags
    private MyQueue<String> errorQueue;  // Queue to store error messages

    public XMLParser() {
        tagStack = new MyStack<>();
        errorQueue = new MyQueue<>();
    }

    /**
     * Parses the specified XML file for errors.
     *
     * @param filePath The path to the XML file to parse.
     */
    public void parseFile(String filePath) {
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            int lineNum = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                parseLine(line, lineNum);
                lineNum++;
            }
            
            displayErrors();
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    /**
     * Parses a single line of the XML file for tags.
     *
     * @param line    The line to parse.
     * @param lineNum The line number in the file.
     */
    private void parseLine(String line, int lineNum) {
        // Enhanced regex to detect tags (opening, closing, and self-closing)
        String tagPattern = "<(/?\\w+)([^>]*)?/?>";

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(tagPattern);
        java.util.regex.Matcher matcher = pattern.matcher(line);
        
        boolean invalidCloseTagDetected = line.contains(">>"); // Check for multiple '>' symbols

        if (invalidCloseTagDetected) {
            // Report an invalid closing tag with the full line content
            errorQueue.enqueue("Invalid close tag at line " + lineNum + ":\n\t" + line); // new line with single-indentation for entire line-tag containing error
            return; // Skip further processing for this line
        }

        while (matcher.find()) {
            String tag = matcher.group(1); // Group 1: Opening or closing tag
            boolean isSelfClosing = line.endsWith("/>");

            if (isSelfClosing) {
                // Self-closing tag; skip adding to stack
                continue;
            }
            processTag(tag, lineNum, line); // Pass the entire line content
        }
    }

    /**
     * Processes a tag, determining if it's an opening or closing tag.
     *
     * @param tag           The tag to process.
     * @param lineNum       The line number of the tag.
     * @param lineContent   The entire line-tag content 
     */
    private void processTag(String tag, int lineNum, String lineContent) {
        if (tag.startsWith("/")) { // Closing tag
            String closingTag = tag.substring(1);
            if (tagStack.isEmpty() || !tagStack.peek().equals(closingTag)) {
                errorQueue.enqueue("Error at line " + lineNum + ":\n\t" + lineContent); // new line with single-indentation for entire line-tag containing error
            } else {
                tagStack.pop(); // Pop the matching opening tag
            }
        } else { // Opening tag
            tagStack.push(tag);
        }
    }

    
    /**
     * Displays errors found during parsing or indicates no errors were found.
     */
    private void displayErrors() {
        System.out.println("=============Error LOG=============");
        System.out.println(); // Blank spacing on the console
        if (errorQueue.isEmpty()) {
            System.out.println("No errors found.");
        } else {
            while (!errorQueue.isEmpty()) {
                System.out.println(errorQueue.dequeue());
            }
        }
    }

    
    /**
     * Main method for executing the XMLParser.
     *
     * @param args Command-line arguments. Expects a single file path.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar Parser.jar <file-path>");
            return;
        }

        String filePath = args[0];
        XMLParser parser = new XMLParser();
        parser.parseFile(filePath);
    }
}  