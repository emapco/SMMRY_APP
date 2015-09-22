package com.manny.smmry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class SMMRY {
    public SMMRY() {
        String url;
        String requestLength;
        String requestKeywordCount;
        String requestNoQuote;
        String requestBreak;
    }

    HashMap<String, String> summarize(HashMap<String, String> parameters) throws IOException {
        ArrayList<String> jsonArray = Request.requestSummry(parameters);

        String json = "";
        for(String line : jsonArray) {
            json += line;
        }

        SMMRYAPI results = Deserializer.JsonDeserializer(json);

        HashMap<String, String> response = new HashMap<String, String>();
        parameters.put("characterCount", results.get_sm_api_character_count());
        parameters.put("title", results.get_sm_api_title());
        parameters.put("content", results.get_sm_api_content());
        parameters.put("APILimitation", results.get_sm_api_limitation());

        return response;
    }
}
