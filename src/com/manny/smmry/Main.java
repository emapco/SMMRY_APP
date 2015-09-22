package com.manny.smmry;

import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            JFrame appFrame = new JFrame("Summarize!");
            appFrame.setContentPane(new App().);
            appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            appFrame.pack();
            appFrame.setVisible(true);
            //HashMap<String, String> options = getQuery();
            //getResults(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done!");
    }

    //Makes a request to the SMMRY API. Formats the json response and print/displays results.
    private static void getResults(HashMap<String, String> options) throws IOException {
        ArrayList<String> jsonArray = Request.requestSummry(options);

        String json = "";
        for(String line : jsonArray) {
            json += line;
        }

        SMMRYAPI results = Deserializer.JsonDeserializer(json);

        printResults(results);
    }

    //Print to file and displays console the response
    private static void printResults(SMMRYAPI response) throws IOException {
        PrintWriter writer = new PrintWriter("SummarizedContent.txt", "UTF-8");

        String characterCount = response.get_sm_api_character_count();
        String title = response.get_sm_api_title();
        String content = response.get_sm_api_content();
        String APILimitation = response.get_sm_api_limitation();

        writer.println("Character Count: " + characterCount);
        writer.println("Title: " + title);
        writer.println("Content: " + content);
        writer.println("API Limitation: " + APILimitation);

        System.out.println("\n\n\nCharacter Count: " + characterCount);
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("API Limitation: " + APILimitation);

        writer.close();
    }

    private static HashMap<String, String> getQuery() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter URL to summarize: ");
        String url = input.nextLine();
        while (!isValidURL(url)) {
            System.out.print("Please enter a valid URL: ");
            url = input.nextLine();
        }

        System.out.print("Please enter sentence length (represents the number of sentences returned, default is 7): ");
        String requestLength = input.nextLine();
        if (!isNumeric(requestLength)) {
            System.out.println("Not a valid number. Skipping parameter setting.");
            requestLength = null;
        }

        System.out.print("Please enter keyword count (represents how many of the top keywords to return): ");
        String requestKeywordCount = input.nextLine();
        if (!isNumeric(requestKeywordCount)) {
            System.out.println("Not a valid number. Skipping parameter setting.");
            requestKeywordCount = null;
        }

        System.out.print("Remove Quotes? true or false? (summary will not include quotations): ");
        String requestNoQuote = input.nextLine();
        if (!requestNoQuote.equals("true")) {
            System.out.println("Not a valid answer. Skipping parameter setting.");
            requestNoQuote = null;
        }

        System.out.print("Include Breaks? true or false? (summary will contain string [BREAK] between each sentence): ");
        String requestBreak = input.nextLine();
        if (!requestBreak.equals("true")) {
            System.out.println("Not a valid answer. Skipping parameter setting.");
            requestBreak = null;
        }

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("url", url);
        parameters.put("requestLength", requestLength);
        parameters.put("requestKeywordCount", requestKeywordCount);
        parameters.put("requestNoQuote", requestNoQuote);
        parameters.put("requestBreak", requestBreak);

        input.close();
        return parameters;
    }

    private static boolean isNumeric(String number) {
        try {
            Integer i = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean isValidURL(String stringURL) {
        try {
            URL url  = new URL(stringURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}