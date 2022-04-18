package forms;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class NotificationForm extends JFrame {

    public NotificationForm(String text) {
        JTextPane textPane = new JTextPane();
        textPane.setText(text);
        textPane.setEditable(false);

        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);

        textPane.setParagraphAttributes(attributes, true);

        add(textPane);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(260, 70));
        setResizable(false);
        setVisible(true);
    }
}