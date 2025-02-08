import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class Main {
    // * define window size information */
    static short width = 600;
    static short height = 900;

    // * define data about the buttons */
    static Dimension buttonSize = new Dimension((width - 60) / 3, (height - 60) / 7);
    static Font buttonfont = new Font("Arial", Font.PLAIN, 40);

    // * set defaults for calculator data */
    static double oldNum = 0;
    static String operator = "";
    static double newNum = 0;
    static boolean usingDecimal = false;
    static short decimals = 0;

    // * create a window */
    static JFrame frame = new JFrame("calculator");

    // * create the screen */
    static JLabel label = new JLabel("testing 123");

    public static void main(String[] args) {
        // * make the window have a grid layout */
        frame.setLayout(new GridBagLayout());

        // * position the window */
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // * position the screen */
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        label.setPreferredSize(new Dimension(width - 60, (height - 60) / 7));

        // * style the screen */
        label.setFont(buttonfont);
        label.setOpaque(true);
        label.setBackground(new Color(100, 200, 255));

        // * add the screen to the window */
        frame.add(label, gbc);

        // * add the numbers to the screen */
        addNumButton(1, 0, 1);
        addNumButton(2, 1, 1);
        addNumButton(3, 2, 1);
        addNumButton(4, 0, 2);
        addNumButton(5, 1, 2);
        addNumButton(6, 2, 2);
        addNumButton(7, 0, 3);
        addNumButton(8, 1, 3);
        addNumButton(9, 2, 3);
        addNumButton(0, 0, 4);

        // * add the operators to the screen */
        addOperatorButton("+", 1, 4);
        addOperatorButton("*", 1, 5);
        addOperatorButton("/", 2, 5);
        addOperatorButton("-", 2, 4);

        // * add the calculate button to the screen */
        addCalculateButton(0, 5);

        //* add other buttons */
        addDecimalButton(0, 6);
        addBackspaceButton(1, 6);
        addDeleteButton(2, 6);

        // * listen for input */
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                System.out.println("Key pressed: " + keyCode);
                if (keyCode == 48 || keyCode == 96) {
                    addNum("0");
                } else if (keyCode == 49 || keyCode == 97) {
                    addNum("1");
                } else if (keyCode == 50 || keyCode == 98) {
                    addNum("2");
                } else if (keyCode == 51 || keyCode == 99) {
                    addNum("3");
                } else if (keyCode == 52 || keyCode == 100) {
                    addNum("4");
                } else if (keyCode == 53 || keyCode == 101) {
                    addNum("5");
                } else if (keyCode == 54 || keyCode == 102) {
                    addNum("6");
                } else if (keyCode == 55 || keyCode == 103) {
                    addNum("7");
                } else if (keyCode == 56 || keyCode == 104) {
                    addNum("8");
                } else if (keyCode == 57 || keyCode == 105) {
                    addNum("9");
                } else if (keyCode == 10) {
                    calculate();
                } else if (keyCode == 106) {
                    setOperator("*");
                } else if (keyCode == 111) {
                    setOperator("/");
                } else if (keyCode == 107) {
                    setOperator("+");
                } else if (keyCode == 109) {
                    setOperator("-");
                } else if (keyCode == 46 || keyCode == 110) {
                    decimal();
                } else if (keyCode == 127) {
                    delete();
                } else if (keyCode == 8) {
                    backspace();
                }
            }
        });

        // * update screen and show window */
        updateNumDisplay();
        frame.setVisible(true);
    }

    public static void delete() {
        frame.requestFocusInWindow();
        oldNum = 0;
        operator = "";
        newNum = 0;
        usingDecimal = false;
        decimals = 0;
        updateNumDisplay();
    }

    public static void backspace() {
        frame.requestFocusInWindow();
        newNum = 0;
        usingDecimal = false;
        decimals = 0;
        updateNumDisplay();
    }

    public static void decimal() {
        frame.requestFocusInWindow();
        usingDecimal = true;
        updateNumDisplay();
    }

    public static void addNum(String buttonInput) {
        frame.requestFocusInWindow();
        int number = Integer.parseInt(buttonInput);
        if (!Objects.equals(operator, "") || oldNum == 0.0) {
            if (usingDecimal) {
                decimals++;
            } else {
                newNum *= 10;
            }
            newNum += number / Math.pow(10, decimals);
        }
        updateNumDisplay();
    }

    public static void setOperator(String buttonInput) {
        frame.requestFocusInWindow();
        if (oldNum == 0) {
            oldNum = newNum;
            newNum = 0;
        }
        if (newNum != 0) {
            calculate();
        }
        operator = buttonInput;
        updateNumDisplay();
    }

    public static void calculate() {
        frame.requestFocusInWindow();
        System.out.printf("%s %s %s%n", oldNum, operator, newNum);
        if (Objects.equals(operator, "*")) {
            oldNum = oldNum * newNum;
        } else if (Objects.equals(operator, "/") && newNum != 0) {
            oldNum = oldNum / newNum;
        } else if (Objects.equals(operator, "+")) {
            oldNum = oldNum + newNum;
        } else if (Objects.equals(operator, "-")) {
            oldNum = oldNum - newNum;
        } else if (Objects.equals(operator, "")) {
            oldNum = newNum;
        }
        newNum = 0;
        operator = "";
        System.out.printf("result calculated: %s %s %s%n", oldNum, operator, newNum);
        updateNumDisplay();
    }

    public static void addDecimalButton(Integer gridx, Integer gridY) {
        ActionListener buttonListener = e -> decimal();
        createButton(buttonListener, ".", gridx, gridY);
    }

    public static void addBackspaceButton(Integer gridx, Integer gridY) {
        ActionListener buttonListener = e -> backspace();
        createButton(buttonListener, "<-", gridx, gridY);
    }

    public static void addDeleteButton(Integer gridx, Integer gridY) {
        ActionListener buttonListener = e -> delete();
        createButton(buttonListener, "x", gridx, gridY);
    }

    public static void addNumButton(Integer number, Integer gridx, Integer gridY) {
        ActionListener buttonListener = e -> addNum(((JButton) e.getSource()).getText());
        createButton(buttonListener, String.valueOf(number), gridx, gridY);
    }

    public static void addOperatorButton(String operator, Integer gridx, Integer gridY) {
        ActionListener buttonListener = e -> setOperator(((JButton) e.getSource()).getText());
        createButton(buttonListener, operator, gridx, gridY);
    }

    public static void addCalculateButton(Integer gridx, Integer gridY) {
        ActionListener buttonListener = e -> calculate();
        createButton(buttonListener, "=", gridx, gridY);
    }

    public static void createButton(ActionListener buttonListener, String displayText, Integer gridx, Integer gridY) {
        // * create a new button */
        GridBagConstraints gbc = new GridBagConstraints();
        // * set the display text */
        JButton num = new JButton(displayText);
        // * set the data */
        num.setPreferredSize(buttonSize);
        num.setFont(buttonfont);
        num.addActionListener(buttonListener);
        // * position the button */
        gbc.gridx = gridx;
        gbc.gridy = gridY;
        // * add the button */
        frame.add(num, gbc);
    }

    public static void updateNumDisplay() {
        String tempOldNum = String.valueOf(oldNum);
        if (tempOldNum.equals("0.0")) {
            tempOldNum = "";
        }
        String tempNewNum = String.valueOf(newNum);
        if (Objects.equals(operator, "") && !tempOldNum.isEmpty()) {
            tempNewNum = "";
        }
        label.setText(String.format("%s %s %s", tempOldNum, operator, tempNewNum));
    }
}
