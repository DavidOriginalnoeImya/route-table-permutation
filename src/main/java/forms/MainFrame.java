package forms;

import algorithm.RtpCipher;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class MainFrame extends JFrame {
    File selectedFile;

    private final JTextArea textArea = new JTextArea();
    private final JMenuItem saveMenuItem = createMenuItem("Сохранить", new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedFile != null) {
                writeToFile(selectedFile, getLastLineFromTextArea());
            }
        }
    }, new Dimension(140, 22));
    private final JMenuItem helpMenuItem = createMenuItem("Помощь", new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = getHelpText();
            JOptionPane.showMessageDialog(null, message,
                    "Помощь", JOptionPane.INFORMATION_MESSAGE);
        }
    }, new Dimension(130, 22));
    private final JMenuItem cipherMenuItem = createMenuItem("Маршрутная", new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame input = new JFrame("Ключ");
            input.setContentPane(new InputForm(input, key -> {
                String encryptedString = RtpCipher.encryptString(getLastLineFromTextArea(), Integer.parseInt(key));
                textArea.append("\n" + encryptedString.trim());
            }).getMainPanel());
            input.setMinimumSize(new Dimension(260, 110));
            input.setVisible(true);
            input.setResizable(false);
            input.setLocationRelativeTo(null);
        }
    }, new Dimension(135, 22));

    private final JMenuItem decipherMenuItem = createMenuItem("Маршрутная", new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame input = new JFrame("Ключ");
            input.setContentPane(new InputForm(input, key -> {
                String decryptedString = RtpCipher.decryptString(getLastLineFromTextArea(), Integer.parseInt(key));
                textArea.append("\n" + decryptedString.trim());
            }).getMainPanel());
            input.setMinimumSize(new Dimension(260, 110));
            input.setVisible(true);
            input.setResizable(false);
            input.setLocationRelativeTo(null);
        }
    }, new Dimension(135, 22));

    private final JMenu cipherMenu = createEncryptionMenu("Зашифровать", cipherMenuItem);
    private final JMenu decipherMenu = createEncryptionMenu("Расшифровать", decipherMenuItem);


    public MainFrame() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(cipherMenu);
        menuBar.add(decipherMenu);
        menuBar.add(createReferenceMenu());
        menuBar.add(createExitMenu());

        textArea.setEnabled(false);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                helpMenuItem.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        setJMenuBar(menuBar);
        add(textArea);
        setTitle("Шифрование методами перестановки");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 400);
        setVisible(true);
    }

    private JMenu createFileMenu() {
        Dimension menuDim = new Dimension(140, 22);

        JMenu fileMenu = new JMenu("               Файл");
        fileMenu.setPreferredSize(new Dimension(menuDim.width + 3, menuDim.height));
        fileMenu.setHorizontalAlignment(SwingConstants.CENTER);



        fileMenu.add(createMenuItem("Создать", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cipherMenu.setEnabled(true);
                decipherMenu.setEnabled(true);
                textArea.setEnabled(true);
            }
        }, menuDim));

        fileMenu.add(createMenuItem("Открыть", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    cipherMenu.setEnabled(true);
                    decipherMenu.setEnabled(true);
                    saveMenuItem.setEnabled(true);
                    textArea.setEnabled(true);

                    selectedFile = fileChooser.getSelectedFile();

                    textArea.setText(readFileContent(selectedFile).trim());
                }
            }
        }, menuDim));

        saveMenuItem.setEnabled(false);
        fileMenu.add(saveMenuItem);

        fileMenu.add(createMenuItem("Сохранить как", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save as");

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    writeToFile(fileChooser.getSelectedFile() , getLastLineFromTextArea());
                }
            }
        }, menuDim));

        fileMenu.add(new JPanel());

        fileMenu.add(createMenuItem("Выход", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }, menuDim));

        return fileMenu;
    }

    private JMenu createReferenceMenu() {
        Dimension menuDim = new Dimension(130, 22);

        JMenu referenceMenu = new JMenu("            Справка");
        referenceMenu.setPreferredSize(new Dimension(menuDim.width + 3, menuDim.height));

        referenceMenu.add(createMenuItem("О программе", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Шифрование методами перестановки\n" +
                        "Назначение: маршрутная табличная перестановка." +
                        "Программа позволяет зашифровывать и расшифровывать введенную строку.\n" +
                        "Автор: Абулашвили Д. А.";

                JOptionPane.showMessageDialog(null, message,
                        "О программе", JOptionPane.INFORMATION_MESSAGE);
            }
        }, menuDim));

        helpMenuItem.setEnabled(false);
        referenceMenu.add(helpMenuItem);

        return referenceMenu;
    }

    private JMenu createExitMenu() {
        JMenu exitMenu = new JMenu("                Выход");
        exitMenu.setPreferredSize(new Dimension(145, 22));

        exitMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        return exitMenu;
    }

    private JMenu createEncryptionMenu(String title, JMenuItem realizedCipherMethod) {
        Dimension menuDim = new Dimension(135, 22);
        String notificationText = "Указанный метод не реализован";

        JMenu encryptionMenu = new JMenu("       " + title);
        encryptionMenu.setPreferredSize(new Dimension(menuDim.width + 3, menuDim.height));
        encryptionMenu.setEnabled(false);

        encryptionMenu.add(realizedCipherMethod);

        encryptionMenu.add(createMenuItem("Вертикальная", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Указанный метод не реализован");
            }
        }, menuDim));

        encryptionMenu.add(createMenuItem("Посимвольная", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Указанный метод не реализован");
            }
        }, menuDim));

        encryptionMenu.add(createMenuItem("Побитовая", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Указанный метод не реализован");
            }
        }, menuDim));

        return encryptionMenu;
    }

    private JMenuItem createMenuItem(String itemTitle, ActionListener actionListener, Dimension menuDim) {
        JMenuItem menuItem = new JMenuItem(itemTitle);
        menuItem.setPreferredSize(menuDim);
        menuItem.addActionListener(actionListener);

        return menuItem;
    }

    private String readFileContent(File file) {
        StringBuilder fileContent = new StringBuilder();

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNext()) {
                fileContent.append(scanner.nextLine()).append("\n");

            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fileContent.toString();
    }

    private void writeToFile(File file, String str) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(str);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLastLineFromTextArea() {
        java.util.List<String> lines = Arrays.asList(textArea.getText().split("\n"));

        return (lines.size() > 0) ? lines.get(lines.size() - 1) : "";
    }

    private String getHelpText() {
        StringBuilder help = new StringBuilder();

        try (Scanner scanner = new Scanner(new FileReader("help.txt"))) {
            while (scanner.hasNext()) {
                help.append(scanner.nextLine()).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return help.toString();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
