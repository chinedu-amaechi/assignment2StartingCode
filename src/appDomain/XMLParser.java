package appDomain;

import implementations.MyStack;
import implementations.MyQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author chyme
 */
public class XMLParser {
    private MyStack<String> tagStack;
    private MyQueue<String> errorQueue;

    public XMLParser() {
        tagStack = new MyStack<>();
        errorQueue = new MyQueue<>();
    }

    public void parseFile(String filePath) {
        File file = new File(filePath);
        System.out.println("Absolute file path: " + file.getAbsolutePath());
        System.out.println("Parsing file: " + filePath);
        
        try
            (Scanner scanner = new Scanner(file)) {
            int lineNum = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                parseLine(line, lineNum);
                lineNum++;
            }
            checkRemainingTags();
            displayErrors();
            
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    
    private void parseLine(String line, int lineNum) {
        // Regex for detecting tags with optional attributes and self-closing tags
        String tagPattern = "<(/?\\w+)([^>]*)?>";

        // Find and process tags within the line
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(tagPattern);
        java.util.regex.Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String tag = matcher.group(1);
            processTag(tag, lineNum);
        }
    }

    private void processTag(String tag, int lineNum) {
        tag = tag.replaceAll("[<>]", ""); // Remove angle brackets

        if (tag.startsWith("/")) { // Closing tag
            String closingTag = tag.substring(1);
            if (tagStack.isEmpty() || !tagStack.peek().equals(closingTag)) {
                errorQueue.enqueue("Error at line " + lineNum + ": unmatched closing tag </" + closingTag + ">");
            } else {
                tagStack.pop(); // Pop matching opening tag
            }
        } else if (tag.endsWith("/")) { // Self-closing tag
            // Ignore self-closing tags for error tracking
        } else { // Opening tag
            tagStack.push(tag);
        }
    }

    private void checkRemainingTags() {
        while (!tagStack.isEmpty()) {
            errorQueue.enqueue("Unclosed tag <" + tagStack.pop() + ">");
        }
    }

    private void displayErrors() {
        System.out.println("===================Error LOG============");
        if (errorQueue.isEmpty()) {
            System.out.println("No errors found.");
        } else {
            while (!errorQueue.isEmpty()) {
                System.out.println(errorQueue.dequeue());
            }
        }
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar Parser.jar [file-path]");
            return;
        }

        XMLParser parser = new XMLParser();
        parser.parseFile(args[0]);
           
    }
}