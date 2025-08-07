import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField textField;
    private String operator;
    private double firstNumber;
    private boolean startNewNumber = true;

    public Calculator() {
        setTitle("Simple Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        textField = new JTextField("0");
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        setLayout(new BorderLayout(5, 5));
        add(textField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            if (startNewNumber) {
                textField.setText(command.equals(".") ? "0." : command);
                startNewNumber = false;
            } else {
                if (command.equals(".") && textField.getText().contains(".")) {
                    // Avoid multiple decimals
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
            startNewNumber = true;
        } else if ("=".equals(command)) {
            double secondNumber;
            try {
                secondNumber = Double.parseDouble(textField.getText());
            } catch (NumberFormatException ex) {
                secondNumber = 0;
            }
            double result = 0;

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
                            JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                            startNewNumber = true;
                            return;
                        }
                        result = firstNumber / secondNumber;
                        break;
                    default:
                        result = secondNumber;
                }
                textField.setText("" + result);
            } catch (Exception ex) {
                textField.setText("Error");
            }
            startNewNumber = true;
        } else if ("C".equals(command)) {
            textField.setText("0");
            operator = "";
            firstNumber = 0;
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}
