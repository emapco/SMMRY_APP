package com.manny.smmry;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;

public class Request {
    private static String baseURL = "http://api.smmry.com/";
    private static String APIKeyLink = "&SM_API_KEY=954D48A067"; // Mandatory, N represents your registered API key.
    private static String APIRequestURL = "&SM_URL="; // Optional, X represents the webpage to summarize.
    private static String APIRequestLength = "&SM_LENGTH="; // Optional, N represents the number of sentences returned, default is 7
    private static String APIRequestKeywordCount = "&SM_KEYWORD_COUNT="; // Optional, N represents how many of the top keywords to return
    private static String APIRequestNoQuote = "&SM_QUOTE_AVOID "; // Optional, summary will not include quotations
    private static String APIRequestBreak = "&SM_WITH_BREAK"; // Optional, summary will contain string [BREAK] between each sentence

    //Make an API call to SUMMRY and returns it response in a formatted json string.
    public static ArrayList<String> requestSummry (HashMap<String, String> parameters) throws IOException {
        String url = parameters.get("url");
        String requestLength = parameters.get("requestLength");
        String requestKeywordCount = parameters.get("requestKeywordCount");
        String requestNoQuote = parameters.get("requestNoQuote");
        String requestBreak = parameters.get("requestBreak");

        String strSummry = baseURL + APIKeyLink + APIRequestURL + url;
        if (requestLength != null)
            strSummry += APIRequestLength + requestLength;
        if (requestKeywordCount != null)
            strSummry += APIRequestKeywordCount + requestKeywordCount;
        if (requestNoQuote != null)
            strSummry += APIRequestNoQuote + requestNoQuote;
        if (requestBreak != null)
            strSummry += APIRequestBreak + requestBreak;

        URL summry = new URL(strSummry);

        System.out.println("requesting summary");

        URLConnection sy = summry.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(sy.getInputStream()));

        String inputLine;
        ArrayList<String> json = new ArrayList<String>();

        while ((inputLine = in.readLine()) != null) {
            json.add(inputLine);
        }
        in.close();

        return json;
    }
}