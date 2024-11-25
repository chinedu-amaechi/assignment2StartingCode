package appDomain;

import implementations.MyQueue;
import implementations.MyStack;
import utilities.EmptyQueueException;
import java.io.*;
import java.util.Scanner;

/**
 * XMLParser.java
 * 
 * @author: Developed collaboratively by Team 3, CPRG304, Fall 2024.
 * 
 * Class Description:
 * This class reads an XML file, parses it to identify structural issues, 
 * and logs any errors related to improper XML tag construction. Errors include:
 * - Missing opening/closing tags.
 * - Mismatched tags.
 * - Malformed tags (e.g., missing '<' or '>').
 * - Unclosed tags.
 * 
 * The class utilizes custom data structures:
 * - `MyStack` to manage opening tags.
 * - `MyQueue` to queue identified errors for sequential processing.
 */
public class XMLParser {
    private static MyQueue<String[]> errorQueue = new MyQueue<>();
    private static MyStack<String[]> tagStack = new MyStack<>();
    private static int lineNum = 1; // Line number tracker for error reporting

    
    /**
     * Reads the XML file line by line and processes each line for errors.
     *
     * @param filePath The path to the XML file to be parsed.
     * @throws IOException If the file cannot be found or accessed.
     */
    private static void FileReader(String filePath) throws IOException {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(filePath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processLine(line);
                lineNum++;
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    
    /**
     * Prints all errors detected during parsing. 
     * If there are unmatched opening tags left in the stack, they are also logged as errors.
     */
    public static void displayErrors() {
        boolean noErrors = true;

        System.out.println("=============Error LOG=============");
        System.out.println(); // Blank spacing on the console

        // Handle unmatched opening tags
        while (!tagStack.isEmpty()) {
            String[] tagInfo = tagStack.pop();
            errorQueue.enqueue(new String[] { "<" + tagInfo[0] + ">", "Unclosed tag", tagInfo[1] });
        }

        // Print errors from the queue
        try {
            while (!errorQueue.isEmpty()) {
                noErrors = false;
                String[] error = errorQueue.dequeue();
                System.out.println(String.format("%s at line %s\n\t%s", error[1], error[2], error[0]));
            }
        } catch (EmptyQueueException e) {
            e.printStackTrace();
        }

        if (noErrors) {
            System.out.println("No errors found.");
        }
    }

    
    /**
     * Processes a single line of XML for structural errors.
     * Identifies and reports issues such as:
     * - Malformed tags.
     * - Unclosed tags.
     * - Extra or unmatched closing tags.
     *
     * @param line The XML line to process.
     */
    private static void processLine(String line) {
        line = line.trim();

        // Skip empty lines, declarations, or comments
        if (line.isEmpty() || line.startsWith("<?xml") || line.startsWith("<!--")) {
            return;
        }

        int currentIndex = 0;

        while (currentIndex < line.length()) {
            int openTagStart = line.indexOf('<', currentIndex);
            int openTagEnd = line.indexOf('>', openTagStart);

            if (openTagStart == -1 && openTagEnd != -1) {
                errorQueue.enqueue(new String[] { line, "Invalid closing tag ", String.valueOf(lineNum) });
            }

            int malformedEnd = line.indexOf('>', currentIndex);
            if (openTagStart > malformedEnd && openTagStart != currentIndex) {
                errorQueue.enqueue(new String[] { line, "Invalid opening tag", String.valueOf(lineNum) });
            }

            if (openTagStart == -1) break;

            if (openTagEnd == -1) {
                errorQueue.enqueue(new String[] { line, "Missing closing tag '>'", String.valueOf(lineNum) });
                return;
            }

            String tagContent = line.substring(openTagStart + 1, openTagEnd).trim();
            String fullTag = line.substring(openTagStart, openTagEnd + 1).trim();

            if (tagContent.contains("=")) {
                if (tagContent.endsWith("/")) {
                    tagContent = tagContent.split(" ")[0] + "/";  
                    fullTag = fullTag.split(" ")[1] + "/>";
                } else {
                    tagContent = tagContent.split(" ")[0];
                    fullTag = fullTag.split(" ")[1] + ">";
                }
            }

            if (tagContent.endsWith("/")) {
                // Self-closing tag, no stack operation required
            } else if (tagContent.startsWith("/")) {
                String endTag = tagContent.substring(1);

                if (!tagStack.isEmpty() && tagStack.peek()[0].equals(endTag)) {
                    tagStack.pop();
                } else {
                    if (tagStack.isEmpty()) {
                        errorQueue.enqueue(new String[] { fullTag, "Unmatched closing tag", String.valueOf(lineNum) });
                    } else {
                        boolean matched = false;
                        MyStack<String[]> tempStack = new MyStack<>();

                        while (!tagStack.isEmpty()) {
                            String[] topTag = tagStack.pop();
                            if (topTag[0].equals(endTag)) {
                                matched = true;
                                break;
                            }
                            tempStack.push(topTag);
                        }

                        if (matched) {
                            while (!tempStack.isEmpty()) {
                                String[] tempTag = tempStack.pop();
                                errorQueue.enqueue(new String[] { "<" + tempTag[0] + ">", "Unclosed tag", tempTag[1] });
                            }
                        } else {
                            errorQueue.enqueue(new String[] { fullTag, "Error at ", String.valueOf(lineNum) });
                            while (!tempStack.isEmpty()) {
                                tagStack.push(tempStack.pop());
                            }
                        }
                    }
                }
            } else {
                tagStack.push(new String[] { tagContent, String.valueOf(lineNum) });
            }

            currentIndex = openTagEnd + 1;
        }
    }

    
    /**
     * Entry point for the XML parser program.
     * Ensures correct arguments are provided, reads the XML file, and logs parsing errors.
     *
     * @param args Command-line arguments. The first argument must be the XML file path.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar XMLParser.jar <file-path>");
            return;
        }

        String filePath = args[0];
        try {
            FileReader(filePath);
            displayErrors();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
