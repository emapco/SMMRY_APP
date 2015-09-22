package com.manny.smmry;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;


public class App extends JPanel {
    private JTextField urlTF;
    private JTextField lengthTF;
    private JTextField keywordTF;
    private JButton submitButton;
    private JCheckBox removeQuotesCheckBox;
    private JCheckBox includeBreaksCheckBox;
    private JTabbedPane tabbedPane1;
    private JTextPane summaryTextPane;
    private JTextPane characterCountTextPane;
    private JTextPane APILimitation;
    private JTextPane titleTextPane;

    public App()  {
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("url", urlTF.getText());
                parameters.put("requestLength", lengthTF.getText());
                parameters.put("requestKeywordCount", keywordTF.getText());
                parameters.put("requestNoQuote", Boolean.toString(removeQuotesCheckBox.isSelected()));
                parameters.put("requestBreak", Boolean.toString(includeBreaksCheckBox.isSelected()));

                try {
                    SMMRY sy = new SMMRY();
                    HashMap<String, String> response = sy.summarize(parameters);

                    String title = response.get("title");
                    String content = response.get("content");
                    String characterCount = response.get("characterCount");
                    String APILimit = response.get("APILimitation");

                    titleTextPane.setText(title);
                    summaryTextPane.setText(content);
                    characterCountTextPane.setText(characterCount);
                    APILimitation.setText(APILimit);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            }
        });
    }
}


