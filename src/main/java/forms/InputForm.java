package forms;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputForm {
    private JTextField textField1;
    private JPanel mainPanel;
    private JButton okButton;
    private JButton escButton;

    private String key = "";

    public InputForm(JFrame parent, FuncInt funcInt) {

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key = getInputText();

                if ("".equals(key) || !StringUtils.isNumeric(key)) {
                    JOptionPane.showMessageDialog(null, "Значение ключа неправильное");
                    textField1.setText("");
                }
                else {
                    funcInt.setString(key);
                    parent.dispose();
                }
            }
        });
        escButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.dispose();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getInputText() {
        return textField1.getText();
    }
}
