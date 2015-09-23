package com.manny.smmry;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class App extends JFrame {
    private JTextField urlTF;
    private JTextField lengthTF;
    private JTextField keywordTF;
    private JButton submitButton;
    private JCheckBox removeQuotesCheckBox;
    private JCheckBox includeBreaksCheckBox;
    private JTabbedPane tabbedPane;
    private JTextPane summaryTextPane;
    private JTextPane summaryInfoTextPane;
    private JTextPane APIMessages;
    private JTextPane titleTextPane;
    private JPanel rootPanel;
    private JPanel settingPanel;
    private JPanel textPanel;

    public App()  {
        setContentPane(rootPanel);
        setTitle("Article Summarizer");
        try {
            setIconImage(ImageIO.read(new File("SMMRY_ICON.png")));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(urlTF.getText() != null && isValidURL(urlTF.getText())){
                    requestAndUpdateApplication();
                } else {
                    titleTextPane.setText("Not valid URL. Please enter a valid URL.");
                    summaryTextPane.setText("");
                    summaryInfoTextPane.setText("");
                    APIMessages.setText("");
                }
            }
        });

        setVisible(true);
    }

    private void requestAndUpdateApplication() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("url", urlTF.getText());
        parameters.put("requestNoQuote", Boolean.toString(removeQuotesCheckBox.isSelected()));
        parameters.put("requestBreak", Boolean.toString(includeBreaksCheckBox.isSelected()));
        parameters.put("requestKeywordCount", keywordTF.getText());
        if (!lengthTF.getText().isEmpty()) {
            if (isNumeric(lengthTF.getText()))
                parameters.put("requestLength", lengthTF.getText());
        }
        if (!keywordTF.getText().isEmpty()) {
            if (isNumeric(keywordTF.getText()))
                parameters.put("requestKeywordCount", keywordTF.getText());
        }


        SMMRYAPI response = requestSmmry(parameters);
        String title = response.get_sm_api_title();
        String content = response.get_sm_api_content();
        String characterCount = response.get_sm_api_character_count();
        String APILimit = response.get_sm_api_limitation();
        String APIError = response.get_sm_api_error();
        String APIMessage = response.get_sm_api_message();
        int creditCost = response.get_sm_api_credit_cost();
        String creditBalance = response.get_sm_api_credit_balance();
        String[] keywordsArray = response.get_sm_api_keyword_array();


        String keywordsString = "";
        if (keywordsArray != null && keywordsArray.length>0) {
            for(String keyword : keywordsArray) {
                keywordsString += keyword + ", ";
            }
            keywordsString = keywordsString.substring(0, keywordsString.length()-2);
        }


        String summaryInfo = "Character Count: " + characterCount
                + "\nKeywords: " + keywordsString;

        String APIText = "APILimit: " + APILimit
                + "\nAPIError: " + APIError + "\nAPIMessage: " + APIMessage
                + "\nCredit Cost: " + creditCost + "\nCredit Balance: " + creditBalance;


        titleTextPane.setText(title);
        summaryTextPane.setText(content);
        summaryInfoTextPane.setText(summaryInfo);
        APIMessages.setText(APIText);
    }

    private static SMMRYAPI requestSmmry(HashMap<String, String> parameters) {
        try {
            ArrayList<String> jsonArray = Request.requestSummry(parameters);

            String json = "";
            for (String line : jsonArray)
                json += line;

            return Deserializer.JsonDeserializer(json);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
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


