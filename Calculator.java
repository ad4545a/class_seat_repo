import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Calculator extends JFrame implements ActionListener {
    private JTextField textField;
    private String operator = "";
    private double firstNumber = 0;
    private boolean startNewNumber = true;
    private final ArrayList<String> history = new ArrayList<>();
    private final String HISTORY_BUTTON_LABEL = "History";
    private final String CLEAR_BUTTON_LABEL = "C";

    private JPanel buttonPanel;

    // Dark theme and OnePlus inspired colors
    private final Color backgroundColor = Color.BLACK;
    private final Color textFieldBgColor = new Color(30, 30, 30);  // Very dark gray for textfield
    private final Color textFieldFgColor = Color.WHITE;            // White font for textfield

    private final Color numberBtnBase = new Color(50, 50, 50);          // Dark gray
    private final Color numberBtnHover = new Color(80, 80, 80);         // Lighter gray on hover
    private final Color operatorBtnBase = new Color(255, 69, 0);        // Bright orange-red
    private final Color operatorBtnHover = new Color(255, 99, 71);      // Lighter red-orange hover
    private final Color specialBtnBase = new Color(70, 130, 180);       // Steel blue for Clear & History
    private final Color specialBtnHover = new Color(100, 149, 237);     // Cornflower blue on hover

    public Calculator() {
        setTitle("Simple Calculator");
        setSize(450, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(backgroundColor);

        // Text field setup
        textField = new JTextField("0");
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.BOLD, 36));
        textField.setBackground(textFieldBgColor);
        textField.setForeground(textFieldFgColor);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textField.setCaretColor(textFieldFgColor);

        // Button panel setup
        buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new GridBagLayout());

        // Buttons layout (6 rows x 4 columns = 24 spots)
        String[] buttonOrder = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                CLEAR_BUTTON_LABEL, HISTORY_BUTTON_LABEL, "", ""
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int i = 0; i < buttonOrder.length; i++) {
            String text = buttonOrder[i];
            JButton button;

            if (text.isEmpty()) { // placeholder
                button = new JButton();
                button.setEnabled(false);
                button.setBorder(null);
                button.setOpaque(false);
                button.setBackground(backgroundColor);
            } else {
                button = new JButton(text);

                boolean isOperator = "+-*/=".contains(text);
                boolean isSpecial = (text.equals(CLEAR_BUTTON_LABEL) || text.equals(HISTORY_BUTTON_LABEL));

                // Set base colors and fonts for dark theme
                if (isOperator) {
                    button.setBackground(operatorBtnBase);
                    button.setForeground(Color.WHITE);
                    button.setFont(new Font("Arial", Font.BOLD, 28));
                    button.setPreferredSize(new Dimension(80, 70));
                } else if (isSpecial) {
                    button.setBackground(specialBtnBase);
                    button.setForeground(Color.WHITE);
                    button.setFont(new Font("Arial", Font.BOLD, 28));
                    button.setPreferredSize(new Dimension(90, 70));
                } else {
                    button.setBackground(numberBtnBase);
                    button.setForeground(Color.WHITE);
                    button.setFont(new Font("Arial", Font.BOLD, 28));
                    button.setPreferredSize(new Dimension(80, 70));
                }

                button.setOpaque(true);
                button.setBorder(new BevelBorder(BevelBorder.RAISED));
                button.setFocusPainted(false);

                // Add hover effect
                Color baseColor = button.getBackground();
                Color hoverColor = isOperator ? operatorBtnHover :
                                   isSpecial ? specialBtnHover : numberBtnHover;

                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        button.setBackground(hoverColor);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        button.setBackground(baseColor);
                    }
                });

                button.addActionListener(this);
            }

            gbc.gridx = i % 4;
            gbc.gridy = i / 4;
            gbc.gridheight = 1;
            buttonPanel.add(button, gbc);
        }

        setLayout(new BorderLayout(12, 12));
        add(textField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(HISTORY_BUTTON_LABEL)) {
            showHistory();
            return;
        }

        if (command.equals(CLEAR_BUTTON_LABEL)) {
            clearCalculator();
            return;
        }

        if ("0123456789.".contains(command)) {
            if (startNewNumber) {
                textField.setText(command.equals(".") ? "0." : command);
                startNewNumber = false;
            } else {
                // Do not allow multiple decimals
                if (command.equals(".") && textField.getText().contains(".")) {
                    return;
                }
                textField.setText(textField.getText() + command);
            }
        } else if ("+-*/".contains(command)) {
            try {
                firstNumber = Double.parseDouble(textField.getText());
            } catch (NumberFormatException ex) {
                firstNumber = 0;
            }
            operator = command;

            // Display first number and operation symbol
            textField.setText(formatNumber(firstNumber) + " " + operator + " ");
            startNewNumber = true;
        } else if ("=".equals(command)) {
            double secondNumber;
            try {
                secondNumber = Double.parseDouble(textField.getText().replaceAll("[+\\-*/ ]", ""));
            } catch (NumberFormatException ex) {
                secondNumber = 0;
            }

            double result;
            try {
                switch (operator) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    case "*":
                        result = firstNumber * secondNumber;
                        break;
                    case "/":
                        if (secondNumber == 0) {
                            JOptionPane.showMessageDialog(this,
                                    "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                            startNewNumber = true;
                            return;
                        }
                        result = firstNumber / secondNumber;
                        break;
                    default:
                        result = secondNumber;
                }
                textField.setText(formatNumber(result));

                // Save calculation in history
                String historyEntry = String.format("%s %s %s = %s",
                        formatNumber(firstNumber), operator, formatNumber(secondNumber), formatNumber(result));
                history.add(historyEntry);

            } catch (Exception ex) {
                textField.setText("Error");
            }
            startNewNumber = true;
            operator = "";
        }
    }

    private void clearCalculator() {
        textField.setText("0");
        operator = "";
        firstNumber = 0;
        startNewNumber = true;
    }

    private void showHistory() {
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No history available.", "History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String entry : history) {
            sb.append(entry).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setBackground(textFieldBgColor);
        textArea.setForeground(textFieldFgColor);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 400));
        scrollPane.getViewport().setBackground(textFieldBgColor);

        JOptionPane.showMessageDialog(this, scrollPane, "Calculation History", JOptionPane.INFORMATION_MESSAGE);
    }

    // Format numbers without unnecessary trailing zeros
    private String formatNumber(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%.6f", d).replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}
