package com.manny.smmry;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
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
    private JTextPane characterCountTextPane;
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

                try {
                    ArrayList<String> jsonArray = Request.requestSummry(parameters);

                    String json = "";
                    for (String line : jsonArray) {
                        json += line;
                    }

                    SMMRYAPI response = Deserializer.JsonDeserializer(json);

                    String title = response.get_sm_api_title();
                    String content = response.get_sm_api_content();
                    String characterCount = response.get_sm_api_character_count();
                    String APILimit = response.get_sm_api_limitation();
                    String APIError = response.get_sm_api_error();
                    String APIMessage = response.get_sm_api_message();

                    String APIText = "APILimit: " + APILimit
                            + "\nAPIError: " + APIError + "\nAPIMessage: " + APIMessage;

                    titleTextPane.setText(title);
                    summaryTextPane.setText(content);
                    characterCountTextPane.setText(characterCount);
                    APIMessages.setText(APIText);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            }
        });

        setVisible(true);
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
}


