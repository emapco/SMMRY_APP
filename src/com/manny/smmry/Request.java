package com.manny.smmry;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;

/**
Generates url string for API call, creates connections, parses results and returns a formatted JSON string
**/
public class Request {
    private static String baseURL = "http://api.smmry.com/";
    private static String APIKeyLink = "&SM_API_KEY=N"; // Mandatory, N represents your registered API key.
    private static String APIRequestURL = "&SM_URL="; // Optional, X represents the webpage to summarize.
    private static String APIRequestLength = "&SM_LENGTH="; // Optional, N represents the number of sentences returned, default is 7
    private static String APIRequestKeywordCount = "&SM_KEYWORD_COUNT="; // Optional, N represents how many of the top keywords to return
    private static String APIRequestNoQuote = "&SM_QUOTE_AVOID"; // Optional, summary will not include quotations
    private static String APIRequestBreak = "&SM_WITH_BREAK"; // Optional, summary will contain string [BREAK] between each sentence

    // Makes url string for api call
    private static String urlBuilder (HashMap<String, String> parameters):
        // gets strings from UI
        String url = parameters.get("url");
        String requestLength = parameters.get("requestLength");
        String requestKeywordCount = parameters.get("requestKeywordCount");
        String requestNoQuote = parameters.get("requestNoQuote");
        String requestBreak = parameters.get("requestBreak");
    
        String strSmmry = baseURL + APIKeyLink;
            if (requestKeywordCount != null)
                strSmmry += APIRequestKeywordCount + requestKeywordCount;
            if (requestLength != null)
                strSmmry += APIRequestLength + requestLength;
            if (requestNoQuote.equals("true"))
                strSmmry += APIRequestNoQuote;
            if (requestBreak.equals("true"))
                strSmmry += APIRequestBreak;
            if (url != null)
                strSmmry += APIRequestURL + url;
        return strSmmry
          
    // Makes an API call to SUMMRY and returns the response as a formatted json string.
    public static SMMRYAPI requestSummry (HashMap<String, String> parameters) throws IOException {
        strSmmry = urlBuilder()
        URL summry = new URL(strSummry);

        URLConnection sy = summry.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(sy.getInputStream()));
        
        String inputLine;
        ArrayList<String> jsonArray = new ArrayList<>();
            
        while ((inputLine = in.readLine()) != null) {
            jsonArray.add(inputLine);
        }
        in.close();

        String json = "";
        for (String line : jsonArray)
            json += line;

        return Deserializer.JsonDeserializer(json);
    }
}
